package org.student.filmApp.parser;

import com.google.common.base.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.student.filmApp.parser.ParserUtils.*;


public class FilmParser {

    static final String GENRE_FILM_INFO_LABEL = "gatunek:";
    static final String COUNTRY_FILM_INFO_LABEL = "produkcja:";
    static final String RELEASE_DATES_FILM_INFO_LABEL = "premiera:";
    static final String BOXOFFICE_FILM_INFO_LABEL = "boxoffice:";

    static final String WORLDWIDE_RELEASE_DATE_SPECIFIC = "świat";
    static final String POLISH_RELEASE_DATE_SPECIFIC = "Polska";

    static final String DAY_OF_MONTH_PATTERN = "[1-3]?[0-9]";
    static final String POLISH_WORD_PATTERN = "[a-pr-uwy-zA-PR-UWY-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*";
    static final String YEAR_PATTERN = "[12][0-9]{3}";
    static final String RELEASE_DATE_PATTERN = "(" + DAY_OF_MONTH_PATTERN + " " + POLISH_WORD_PATTERN + " " + YEAR_PATTERN + " \\((?:" +
            WORLDWIDE_RELEASE_DATE_SPECIFIC + "|" + POLISH_RELEASE_DATE_SPECIFIC +
            ")\\))";

    static final List<String> FILM_INFO_LABEL;
    static {
        FILM_INFO_LABEL = Arrays.asList(GENRE_FILM_INFO_LABEL, COUNTRY_FILM_INFO_LABEL, RELEASE_DATES_FILM_INFO_LABEL, BOXOFFICE_FILM_INFO_LABEL);
    }
    static public String parse(int filmId) throws IOException, ParseException {
        Document doc = Jsoup.connect("http://www.filmweb.pl/Podziemny.Krag").get();

        Element storylineEl = doc.select("div.filmPlot.bottom-15 p").first();
        Optional<String> storyline = Optional
                .ofNullable(storylineEl.text())
                .filter(s -> !s.contains("Ten film nie ma jeszcze zarysu fabuły."));

        Elements filmInfoRows = doc.select("div.filmInfo.bottom-15 table tr");
        List<Element> requiredFilmInfoRows = filmInfoRows
                .stream()
                .filter(element ->
                        element.children().first().is("th") &&
                                FILM_INFO_LABEL.contains(element.children().first().text())
                )
                .collect(Collectors.toList());

        String splittedReleaseDates = findReleaseDates(requiredFilmInfoRows);

        Pattern pattern = Pattern.compile(RELEASE_DATE_PATTERN);
        Matcher matcher = pattern.matcher(splittedReleaseDates);

        Optional<String> polishReleaseDate = Optional.empty();
        Optional<String> worldwideReleaseDate = Optional.empty();
        while(matcher.find()) {
            if(matcher.group().contains(WORLDWIDE_RELEASE_DATE_SPECIFIC)) {
                worldwideReleaseDate = convertToRequiredDateFormat(matcher.group());
            } else {
                polishReleaseDate = convertToRequiredDateFormat(matcher.group());
            }
        }

        Elements mainTitleDiv = doc.select("div.bottom-15 div.s-32.top-5");
        String mainTitle = mainTitleDiv.select("h1 a").text();
        Elements originalTitleHeader = mainTitleDiv.next("h2");
        Optional<String> originalTitle = Optional
                .ofNullable(originalTitleHeader.text())
                .filter(t -> !t.isEmpty());

        Optional<String> runningTime = Optional
                .ofNullable(doc.select("div.filmMainHeader time").first().attr("datetime"))
                .map(t -> t.replaceAll("[PTM]", ""));

        Optional<String> boxoffice = findBoxoffice(filmInfoRows);

        String insertStatement = "INSERT INTO FILM(" +
                    "ORIGINAL_TITLE, " +
                    "POLISH_TITLE, " +
                    "WORLDWIDE_RELEASE_DATE, " +
                    "POLISH_RELEASE_DATE, " +
                    "BOX_OFFICE, " +
                    "RUNNING_TIME, " +
                    "STORYLINE) " +
                "VALUES(" +
                    DB_STRING_MAPPER.apply(mainTitle) + ", " +
                    originalTitle.map(DB_STRING_MAPPER).orElse("null") + ", " +
                    worldwideReleaseDate.map(DB_DATE_MAPPER).orElse("null") + ", " +
                    polishReleaseDate.map(DB_DATE_MAPPER).orElse("null") + ", " +
                    boxoffice.orElse("null") + ", " +
                    runningTime.orElse("null") + ", " +
                    storyline.map(DB_STRING_MAPPER).orElse("null") + ");";

        System.out.println(insertStatement);
        System.out.println(findCountries(filmInfoRows));

        for(String c : findCountries(filmInfoRows)) {
            System.out.println("INSERT INTO FILM_COUNTRY(FILM_ID, COUNTRY_ID) VALUES(" + filmId + ", " + countriesByName.get(c) + ");");
        }

        for(String g : findGenres(filmInfoRows)) {
            System.out.println("INSERT INTO FILM_GENRE(FILM_ID, GENRE_ID) VALUES(" + filmId + ", " + genresByName.get(g) + ");");
        }

        return null;
    }

    private static Optional<String> findBoxoffice(List<Element> elements) {
        return elements
                .stream()
                .filter(element -> element.children().first().text().equals(BOXOFFICE_FILM_INFO_LABEL))
                .map(g -> g.select("td"))
                .map(a -> a.text().replaceAll("[$ ]", ""))
                .findFirst();
    }

    private static List<String> findGenres(List<Element> elements) {

        Optional<String> mergedGenres = elements
                .stream()
                .filter(element -> element.children().first().text().equals(GENRE_FILM_INFO_LABEL))
                .map(g -> g.select("td ul li a"))
                .map(a -> a.text())
                .findFirst();

        List<String> genres = new ArrayList<>();
        mergedGenres.ifPresent(c -> genres.addAll(Arrays.asList(c.split(" "))));

        return genres;
    }

    private static List<String> findCountries(List<Element> elements) {
        Optional<String> mergedCountries = elements
                .stream()
                .filter(element ->
                        element.children().first().text().equals(COUNTRY_FILM_INFO_LABEL))
                .map(g -> g.select("td ul li a"))
                .map(a -> a.text())
                .findFirst();

        List<String> countries = new ArrayList<>();
        mergedCountries.ifPresent(c -> countries.addAll(Arrays.asList(c.split(" "))));

        return countries;
    }

    private static String findReleaseDates(List<Element> elements) {
        return elements
                .stream()
                .filter(element ->
                        element.children().first().text().equals(RELEASE_DATES_FILM_INFO_LABEL))
                .map(g -> g.select("td a"))
                .map(a -> a.text())
                .findFirst()
                .orElse("");

    }

    public static void main(String[] args) throws IOException, ParseException {
        parse(Integer.parseInt(args[0]));
    }

    private static Map<String, Integer> genresByName = new HashMap<>();
    private static Map<String, Integer> countriesByName = new HashMap<>();
    static {
        countriesByName.put("Afganistan", 18);
        countriesByName.put("Albania", 19);
        countriesByName.put("Algieria", 20);
        countriesByName.put("Andora", 21);
        countriesByName.put("Angola", 22);
        countriesByName.put("Antigua i Barbuda", 23);
        countriesByName.put("Arabia Saudyjska", 24);
        countriesByName.put("Argentyna", 25);
        countriesByName.put("Armenia", 26);
        countriesByName.put("Aruba", 27);
        countriesByName.put("Australia", 1);
        countriesByName.put("Austria", 28);
        countriesByName.put("Autonomia Palestyńska", 29);
        countriesByName.put("Azerbejdżan", 30);
        countriesByName.put("Bahamy", 31);
        countriesByName.put("Bahrajn", 32);
        countriesByName.put("Bangladesz", 33);
        countriesByName.put("Barbados", 34);
        countriesByName.put("Belgia", 35);
        countriesByName.put("Belize", 36);
        countriesByName.put("Benin", 37);
        countriesByName.put("Bhutan", 38);
        countriesByName.put("Białoruś", 39);
        countriesByName.put("Birma", 40);
        countriesByName.put("Boliwia", 41);
        countriesByName.put("Bośnia i Hercegowina", 42);
        countriesByName.put("Botswana", 43);
        countriesByName.put("Brazylia", 44);
        countriesByName.put("Brunei", 45);
        countriesByName.put("Bułgaria", 46);
        countriesByName.put("Burkina Faso", 47);
        countriesByName.put("Burundi", 48);
        countriesByName.put("Chile", 49);
        countriesByName.put("Chiny", 50);
        countriesByName.put("Chorwacja", 51);
        countriesByName.put("Cypr", 52);
        countriesByName.put("Czad", 53);
        countriesByName.put("Czarnogóra", 54);
        countriesByName.put("Czechosłowacja", 55);
        countriesByName.put("Czechy", 56);
        countriesByName.put("Dania", 57);
        countriesByName.put("Demokratyczna Republika Konga", 58);
        countriesByName.put("Dominika", 59);
        countriesByName.put("Dominikana", 60);
        countriesByName.put("Dżibuti", 61);
        countriesByName.put("Egipt", 62);
        countriesByName.put("Ekwador", 63);
        countriesByName.put("Erytrea", 64);
        countriesByName.put("Estonia", 65);
        countriesByName.put("Etiopia", 66);
        countriesByName.put("Fed. Rep. Jugosławii", 67);
        countriesByName.put("Fidżi", 68);
        countriesByName.put("Filipiny", 2);
        countriesByName.put("Finlandia", 69);
        countriesByName.put("Francja", 3);
        countriesByName.put("Gabon", 70);
        countriesByName.put("Gambia", 71);
        countriesByName.put("Ghana", 72);
        countriesByName.put("Grecja", 4);
        countriesByName.put("Grenada", 73);
        countriesByName.put("Grenlandia", 74);
        countriesByName.put("Gruzja", 75);
        countriesByName.put("Gujana", 76);
        countriesByName.put("Gwadelupa", 77);
        countriesByName.put("Gwatemala", 78);
        countriesByName.put("Gwinea", 79);
        countriesByName.put("Gwinea Bissau", 80);
        countriesByName.put("Gwinea Równikowa", 81);
        countriesByName.put("Haiti", 82);
        countriesByName.put("Hiszpania", 5);
        countriesByName.put("Holandia", 83);
        countriesByName.put("Honduras", 84);
        countriesByName.put("Hongkong", 6);
        countriesByName.put("Indie", 7);
        countriesByName.put("Indonezja", 85);
        countriesByName.put("Irak", 86);
        countriesByName.put("Iran", 87);
        countriesByName.put("Irlandia", 88);
        countriesByName.put("Islandia", 89);
        countriesByName.put("Izrael", 90);
        countriesByName.put("Jamajka", 91);
        countriesByName.put("Japonia", 8);
        countriesByName.put("Jemen", 92);
        countriesByName.put("Jordania", 93);
        countriesByName.put("Jugosławia", 94);
        countriesByName.put("Kambodża", 95);
        countriesByName.put("Kamerun", 96);
        countriesByName.put("Kanada", 9);
        countriesByName.put("Katar", 97);
        countriesByName.put("Kazachstan", 98);
        countriesByName.put("Kenia", 99);
        countriesByName.put("Kirgistan", 100);
        countriesByName.put("Kiribati", 101);
        countriesByName.put("Kolumbia", 102);
        countriesByName.put("Komory", 103);
        countriesByName.put("Kongo", 104);
        countriesByName.put("Korea", 105);
        countriesByName.put("Korea Południowa", 106);
        countriesByName.put("Korea Północna", 107);
        countriesByName.put("Kosowo", 108);
        countriesByName.put("Kostaryka", 109);
        countriesByName.put("Kuba", 110);
        countriesByName.put("Kuwejt", 111);
        countriesByName.put("Laos", 112);
        countriesByName.put("Lesotho", 113);
        countriesByName.put("Liban", 114);
        countriesByName.put("Liberia", 115);
        countriesByName.put("Libia", 116);
        countriesByName.put("Liechtenstein", 117);
        countriesByName.put("Litwa", 118);
        countriesByName.put("Luksemburg", 119);
        countriesByName.put("Łotwa", 120);
        countriesByName.put("Macedonia", 121);
        countriesByName.put("Madagaskar", 122);
        countriesByName.put("Makau", 123);
        countriesByName.put("Malawi", 124);
        countriesByName.put("Malediwy", 125);
        countriesByName.put("Malezja", 126);
        countriesByName.put("Mali", 127);
        countriesByName.put("Malta", 128);
        countriesByName.put("Maroko", 129);
        countriesByName.put("Martynika", 130);
        countriesByName.put("Mauretania", 131);
        countriesByName.put("Mauritius", 132);
        countriesByName.put("Meksyk", 10);
        countriesByName.put("Mikronezja", 133);
        countriesByName.put("Mołdawia", 134);
        countriesByName.put("Monako", 135);
        countriesByName.put("Mongolia", 136);
        countriesByName.put("Mozambik", 137);
        countriesByName.put("Namibia", 138);
        countriesByName.put("Nepal", 139);
        countriesByName.put("Niemcy", 11);
        countriesByName.put("Niger", 140);
        countriesByName.put("Nigeria", 141);
        countriesByName.put("Nikaragua", 142);
        countriesByName.put("Niue", 143);
        countriesByName.put("Norwegia", 144);
        countriesByName.put("Nowa Zelandia", 145);
        countriesByName.put("NRD", 146);
        countriesByName.put("Oman", 147);
        countriesByName.put("Pakistan", 148);
        countriesByName.put("Palau", 149);
        countriesByName.put("Palestyna", 150);
        countriesByName.put("Panama", 151);
        countriesByName.put("Papua Nowa Gwinea", 152);
        countriesByName.put("Paragwaj", 153);
        countriesByName.put("Peru", 154);
        countriesByName.put("Polska", 12);
        countriesByName.put("Portoryko", 155);
        countriesByName.put("Portugalia", 156);
        countriesByName.put("Republika Środkowoafrykańska", 157);
        countriesByName.put("Republika Zielonego Przylądka", 158);
        countriesByName.put("RFN", 13);
        countriesByName.put("Rosja", 159);
        countriesByName.put("RPA", 160);
        countriesByName.put("Ruanda", 161);
        countriesByName.put("Rumunia", 162);
        countriesByName.put("Sahara Zachodnia", 163);
        countriesByName.put("Saint Lucia", 164);
        countriesByName.put("Saint Vincent i Grenadyny", 165);
        countriesByName.put("Salwador", 166);
        countriesByName.put("Samoa", 167);
        countriesByName.put("San Marino", 168);
        countriesByName.put("Senegal", 169);
        countriesByName.put("Serbia", 170);
        countriesByName.put("Serbia i Czarnogóra", 171);
        countriesByName.put("Seszele", 172);
        countriesByName.put("Sierra Leone", 173);
        countriesByName.put("Singapur", 174);
        countriesByName.put("Słowacja", 175);
        countriesByName.put("Słowenia", 176);
        countriesByName.put("Somalia", 177);
        countriesByName.put("Sri Lanka", 178);
        countriesByName.put("Suazi", 179);
        countriesByName.put("Sudan", 180);
        countriesByName.put("Surinam", 181);
        countriesByName.put("Syjam", 182);
        countriesByName.put("Syria", 183);
        countriesByName.put("Szwajcaria", 184);
        countriesByName.put("Szwecja", 185);
        countriesByName.put("Tadżykistan", 186);
        countriesByName.put("Tajlandia", 187);
        countriesByName.put("Tajwan", 188);
        countriesByName.put("Tanzania", 189);
        countriesByName.put("Togo", 190);
        countriesByName.put("Tonga", 191);
        countriesByName.put("Trynidad i Tobago", 192);
        countriesByName.put("Tunezja", 193);
        countriesByName.put("Turcja", 194);
        countriesByName.put("Turkmenistan", 195);
        countriesByName.put("Tuvalu", 196);
        countriesByName.put("Uganda", 197);
        countriesByName.put("Ukraina", 198);
        countriesByName.put("Urugwaj", 199);
        countriesByName.put("USA", 14);
        countriesByName.put("Uzbekistan", 200);
        countriesByName.put("Vanuatu", 201);
        countriesByName.put("Watykan", 202);
        countriesByName.put("Wenezuela", 203);
        countriesByName.put("Węgry", 204);
        countriesByName.put("Wielka Brytania", 15);
        countriesByName.put("Wietnam", 205);
        countriesByName.put("Wietnam Północny", 206);
        countriesByName.put("Włochy", 16);
        countriesByName.put("Wybrzeże Kości Słoniowej", 207);
        countriesByName.put("Wyspy Marshalla", 208);
        countriesByName.put("Wyspy Owcze", 209);
        countriesByName.put("Zair", 210);
        countriesByName.put("Zambia", 211);
        countriesByName.put("Zimbabwe", 212);
        countriesByName.put("Zjednoczone Emiraty Arabskie", 213);
        countriesByName.put("ZSRR", 17);

        genresByName.put("Akcja", 1);
        genresByName.put("Animacja", 2);
        genresByName.put("Dokumentalny", 3);
        genresByName.put("Dramat", 4);
        genresByName.put("Familijny", 5);
        genresByName.put("Fantasy", 6);
        genresByName.put("Horror", 7);
        genresByName.put("Komedia", 8);
        genresByName.put("Krótkometrażowy", 9);
        genresByName.put("Kryminał", 10);
        genresByName.put("Melodramat", 11);
        genresByName.put("Niemy", 12);
        genresByName.put("Przygodowy", 13);
        genresByName.put("Romans", 14);
        genresByName.put("Sci-Fi", 15);
        genresByName.put("Thriller", 16);
        genresByName.put("Animacja dla dorosłych", 17);
        genresByName.put("Anime", 18);
        genresByName.put("Baśń", 19);
        genresByName.put("Biblijny", 20);
        genresByName.put("Biograficzny", 21);
        genresByName.put("Czarna komedia", 22);
        genresByName.put("Dla dzieci", 23);
        genresByName.put("Dla młodzieży", 24);
        genresByName.put("Dokumentalizowany", 25);
        genresByName.put("Dramat historyczny", 26);
        genresByName.put("Dramat obyczajowy", 27);
        genresByName.put("Dramat sądowy", 28);
        genresByName.put("Dramat społeczny", 29);
        genresByName.put("Dreszczowiec", 30);
        genresByName.put("Edukacyjny", 31);
        genresByName.put("Erotyczny", 32);
        genresByName.put("Etiuda", 33);
        genresByName.put("Fabularyzowany dok.", 34);
        genresByName.put("Fikcja literacka", 35);
        genresByName.put("Film-Noir", 36);
        genresByName.put("Gangsterski", 37);
        genresByName.put("Groteska filmowa", 38);
        genresByName.put("Historyczny", 39);
        genresByName.put("Karate", 40);
        genresByName.put("Katastroficzny", 41);
        genresByName.put("Komedia dokumentalna", 42);
        genresByName.put("Komedia kryminalna", 43);
        genresByName.put("Komedia obycz.", 44);
        genresByName.put("Komedia rom.", 45);
        genresByName.put("Kostiumowy", 46);
        genresByName.put("Musical", 47);
        genresByName.put("Muzyczny", 48);
        genresByName.put("Nowele filmowe", 49);
        genresByName.put("Obyczajowy", 50);
        genresByName.put("Poetycki", 51);
        genresByName.put("Polityczny", 52);
        genresByName.put("Prawniczy", 53);
        genresByName.put("Propagandowy", 54);
        genresByName.put("Przyrodniczy", 55);
        genresByName.put("Psychologiczny", 56);
        genresByName.put("Płaszcza i szpady", 57);
        genresByName.put("Religijny", 58);
        genresByName.put("Satyra", 59);
        genresByName.put("Sensacyjny", 60);
        genresByName.put("Sportowy", 61);
        genresByName.put("Surrealistyczny", 62);
        genresByName.put("Szpiegowski", 63);
        genresByName.put("Sztuki walki", 64);
        genresByName.put("Western", 65);
        genresByName.put("Wojenny", 66);
        genresByName.put("XXX", 67);
    }
}
