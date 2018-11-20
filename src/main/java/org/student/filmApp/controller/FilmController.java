package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.entity.Country;
import org.student.filmApp.entity.Genre;
import org.student.filmApp.service.CountryService;
import org.student.filmApp.service.FilmService;
import org.student.filmApp.service.GenreService;
import org.student.filmApp.utils.DateUtils;

import java.util.*;
import static java.util.stream.Collectors.toMap;

@Controller
public class FilmController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private FilmService filmService;

    public static final String COUNTRIES_ATTRIBUTE_NAME = "countries";
    public static final String GENRES_ATTRIBUTE_NAME = "genres";
    public static final String YERS_ATTRIBUTE_NAME = "years";

    static List<String> ATTRIBUTE_NAMES;

    static {
        ATTRIBUTE_NAMES = Arrays.asList(COUNTRIES_ATTRIBUTE_NAME, GENRES_ATTRIBUTE_NAME, YERS_ATTRIBUTE_NAME);
    }

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    String showFilms(Model model) {

        Set<Country> countries = countryService.findAll();
        model.addAttribute(COUNTRIES_ATTRIBUTE_NAME, countries);

        Set<Genre> genres = genreService.findAll();
        model.addAttribute(GENRES_ATTRIBUTE_NAME, genres);

        model.addAttribute(YERS_ATTRIBUTE_NAME, DateUtils.getYears());

        return "films";
    }

    @RequestMapping(value = "/films",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFilms(@RequestBody MultiValueMap<String, String> filmSearchCriteria, Model model) {

        filmService.findFilmBySearchTerms(filmSearchCriteria);

        Set<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);

        List<String> selectedCountries = filmSearchCriteria.get("countries");
        if(selectedCountries.contains("countries")) {
            countries.stream()
                    .collect(toMap(e -> e, selectedCountries::contains));
        }

        Set<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        model.addAttribute("years", DateUtils.getYears());

        model.addAttribute("criteria", filmSearchCriteria);
        return "films";
    }

}
