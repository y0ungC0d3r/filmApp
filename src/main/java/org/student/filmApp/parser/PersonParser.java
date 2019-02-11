package org.student.filmApp.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.student.filmApp.parser.ParserUtils.*;


public class PersonParser {

    static final String HEIGHT_PERSON_INFO_LABEL = "wzrost:";
    static final String PLACE_OF_BIRTH_PERSON_INFO_LABEL = "miejsce urodzenia:";
    static final String DATE_OF_BIRTH_PERSON_INFO_LABEL = "data urodzenia:";
    static final String DATE_OF_DEATH_PERSON_INFO_LABEL = "data śmierci:";

    static final List<String> PERSON_INFO_LABELS;
    static {
        PERSON_INFO_LABELS = Arrays.asList(HEIGHT_PERSON_INFO_LABEL,
                PLACE_OF_BIRTH_PERSON_INFO_LABEL,
                DATE_OF_BIRTH_PERSON_INFO_LABEL,
                DATE_OF_DEATH_PERSON_INFO_LABEL);
    }

    static public String parse() throws IOException, ParseException {
        Document doc = Jsoup.connect("https://www.filmweb.pl/person/Jim+Uhls-12125").get();

        Element biographyParagraph = doc.select("div.personBiography.pageBox p").first();
        String biographyCandidate = biographyParagraph.ownText() + biographyParagraph.select("span").text();
        Optional<String> biography = Optional.ofNullable(biographyCandidate)
                .filter(b -> !b.contains("jeszcze nie ma biografii na Filmwebie, możesz być pierwszym który ją doda!"));

        Elements personInfoRows = doc.select("div.personInfo.bottom-15 table tr");
        Map<String, String> requiredPersonInfoMap = personInfoRows
                .stream()
                .map(element -> element.children())
                .filter(element ->
                        element.first().is("th") &&
                                PERSON_INFO_LABELS.contains(element.first().text())
                )
                .collect(Collectors.toMap(
                        k -> k.first()
                                .text(),
                        v -> v.next("td")
                                .first()
                                .text())
                );

        Optional<String> placeOfBirth = Optional.ofNullable(requiredPersonInfoMap.get(PLACE_OF_BIRTH_PERSON_INFO_LABEL));
        Optional<String> dateOfBirth = convertToRequiredDateFormat(requiredPersonInfoMap.get(DATE_OF_BIRTH_PERSON_INFO_LABEL));
        Optional<String> dateOfDeath = convertToRequiredDateFormat(requiredPersonInfoMap.get(DATE_OF_DEATH_PERSON_INFO_LABEL));
        Optional<String> height = Optional
                .ofNullable(requiredPersonInfoMap.get(HEIGHT_PERSON_INFO_LABEL))
                .filter(h -> !h.isEmpty());

        Elements mainNameDiv = doc.select("div.bottom-15 div.s-32.top-5");
        String stageName = mainNameDiv.select("h1 a").text();
        Elements fullNameHeader = mainNameDiv.next("h2");
        Optional<String> fullName = Optional
                .ofNullable(fullNameHeader.text())
                .filter(n -> !n.isEmpty());


        String insertStatement = "INSERT INTO PERSON(" +
                    "FULL_NAME, " +
                    "STAGE_NAME, " +
                    "DATE_OF_BIRTH, " +
                    "DATE_OF_DEATH, " +
                    "PLACE_OF_BIRTH, " +
                    "BIOGRAPHY, " +
                    "HEIGHT) " +
                "VALUES(" +
                    fullName.map(DB_STRING_MAPPER).orElse("null") + ", " +
                    DB_STRING_MAPPER.apply(stageName) + ", " +
                    dateOfBirth.map(DB_DATE_MAPPER).orElse("null") + ", " +
                    dateOfDeath.map(DB_DATE_MAPPER).orElse("null") + ", " +
                    placeOfBirth.map(DB_STRING_MAPPER).orElse("null") + ", " +
                    biography.map(DB_STRING_MAPPER).orElse("null") + ", " +
                    height.map(DB_HEIGHT_MAPPER).orElse("null") + ");";

        System.out.println(insertStatement);

        return null;
    }

    public static void main(String[] args) throws IOException, ParseException {
        parse();
    }
}
