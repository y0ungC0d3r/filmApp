package org.student.filmApp.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class PersonParser {

    static final String HEIGHT_PERSON_INFO_LABEL = "gatunek:";
    static final String PLACE_OF_BIRTH_PERSON_INFO_LABEL = "produkcja:";
    static final String DATE_OF_BIRTH__DATES_PERSON_INFO_LABEL = "premiera:";

    static final String WORLDWIDE_RELEASE_DATE_SPECIFIC = "świat";
    static final String POLISH_RELEASE_DATE_SPECIFIC = "Polska";

    static final String DAY_OF_MONTH_PATTERN = "[1-3]?[0-9]";
    static final String POLISH_WORD_PATTERN = "[a-pr-uwy-zA-PR-UWY-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]*";
    static final String YEAR_PATTERN = "[12][0-9]{3}";
    static final String RELEASE_DATE_PATTERN = "(" + DAY_OF_MONTH_PATTERN + " " + POLISH_WORD_PATTERN + " " + YEAR_PATTERN + " \\((?:" +
            WORLDWIDE_RELEASE_DATE_SPECIFIC + "|" + POLISH_RELEASE_DATE_SPECIFIC +
            ")\\))";

    static final List<String> PERSON_INFO_LABEL;
    static {
        PERSON_INFO_LABEL = Arrays.asList(HEIGHT_PERSON_INFO_LABEL, PLACE_OF_BIRTH_PERSON_INFO_LABEL, DATE_OF_BIRTH__DATES_PERSON_INFO_LABEL);
    }
    static public String parse() throws IOException, ParseException {
        Document doc = Jsoup.connect("http://www.filmweb.pl/person/Brad.Pitt").get();

        Element biographyParagraph = doc.select("div.personBiography.pageBox p").first();
        String biography = biographyParagraph.text();
        System.out.println(biography);

        Elements personInfoRows = doc.select("div.personInfo.bottom-15 table tr");
        List<Element> requiredPersonInfoRows = personInfoRows
                .stream()
                .filter(element ->
                        element.children().first().is("th") &&
                                PERSON_INFO_LABEL.contains(element.children().first().text())
                )
                .collect(Collectors.toList());

        personInfoRows.forEach(System.out::println);

        return null;
    }

    /*private static List<String> findGenres(List<Element> elements) {
        return elements
                .stream()
                .filter(element -> element.children().first().text().equals(GENRE_FILM_INFO_LABEL))
                .map(g -> g.select("td ul li a"))
                .map(a -> a.text())
                .collect(Collectors.toList());

    }

    private static List<String> findCountries(List<Element> elements) {
        return elements
                .stream()
                .filter(element ->
                        element.children().first().text().equals(COUNTRY_FILM_INFO_LABEL))
                .map(g -> g.select("td ul li a"))
                .map(a -> a.text())
                .collect(Collectors.toList());

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

    }*/

    public static void main(String[] args) throws IOException, ParseException {
        parse();
    }
}
