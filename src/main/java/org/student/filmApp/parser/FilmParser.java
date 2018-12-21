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

import static org.student.filmApp.parser.ParserUtils.convertToRequiredDateFormat;


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
        Document doc = Jsoup.connect("http://www.filmweb.pl/Podziemny.Krag").get();

        Element sotrylineEl = doc.select("div.filmPlot.bottom-15 p").first();
        String storyline = sotrylineEl.text();

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
        String originalTitle = originalTitleHeader.text();
        System.out.println(originalTitle);

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

    public static void main(String[] args) throws IOException, ParseException {
        parse();
    }
}
