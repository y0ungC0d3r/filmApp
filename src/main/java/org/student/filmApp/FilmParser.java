package org.student.filmApp;

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
    static public String parse() throws IOException, ParseException {
        Document doc = Jsoup.connect("http://www.filmweb.pl/film/Mandarynki-2013-694062").get();
        Element first = doc.select("div.filmPlot.bottom-15 p").first();
        System.out.println(first.text());

        Elements filmInfoRows = doc.select("div.filmInfo.bottom-15 table tr");
        List<Element> wantedFilmInfoRows = filmInfoRows
                .stream()
                .filter(element ->
                        element.children().first().is("th") &&
                                FILM_INFO_LABEL.contains(element.children().first().text())
                )
                .collect(Collectors.toList());

        String splittedReleaseDates = findReleaseDates(wantedFilmInfoRows);

        Pattern pattern = Pattern.compile(RELEASE_DATE_PATTERN);
        Matcher matcher = pattern.matcher(splittedReleaseDates);

        Optional<String> polishReleaseDate = Optional.empty();
        Optional<String> worldwideReleaseDate = Optional.empty();
        while(matcher.find()) {
            if(matcher.group().contains(WORLDWIDE_RELEASE_DATE_SPECIFIC)) {
                worldwideReleaseDate = convertToRequiredDateFormat(Optional.of(matcher.group()));
            } else {
                polishReleaseDate = convertToRequiredDateFormat(Optional.of(matcher.group()));
            }
        }

        System.out.println(worldwideReleaseDate.get());
        System.out.println(polishReleaseDate.get());


        return null;
    }

    private static List<String> findGenres(List<Element> elements) {
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

    }

    private static Optional<String> convertToRequiredDateFormat(Optional<String> dateOpt) throws ParseException {
        String date = dateOpt.get();
        String[] dateParts = Arrays.copyOf(date.split(" "), date.length() - 1);
        String dayOfMonth = dateParts[0].length() == 1 ? "0" + dateParts[0] : dateParts[0];
        String month = converMonth(dateParts[1]);
        String year = dateParts[2];
        return Optional.of(dayOfMonth + month + year);
    }

    private static String converMonth(String month) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = dateFormat.parse(month);
        dateFormat.applyPattern("MM");
        return dateFormat.format(date);
    }

    public static void main(String[] args) throws IOException, ParseException {
        parse();
    }
}
