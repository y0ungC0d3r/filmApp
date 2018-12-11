package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.entity.Country;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.Genre;
import org.student.filmApp.service.CountryService;
import org.student.filmApp.service.FilmService;
import org.student.filmApp.service.GenreService;
import org.student.filmApp.utils.DateUtils;
import org.student.filmApp.utils.PaginationUtils;

import java.util.*;
import java.util.function.Function;

import static org.student.filmApp.Consts.*;
import static org.student.filmApp.utils.CollectionUtils.*;
import static org.student.filmApp.utils.PaginationUtils.*;

@Controller
public class FilmController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private FilmService filmService;

    public static final String RATING_RANGE_VALUE_PATTERN = "(floor|roof)-[1-5]";

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    String showFilms(Model model) {

        Map<Country, Boolean> markedCountries = createMarkedIdentifiableElementsMap(countryService.findAll(), Collections.emptyList());
        model.addAttribute(COUNTRIES_ATTRIBUTE_NAME, markedCountries);

        Map<Genre, Boolean> markedGenres = createMarkedIdentifiableElementsMap(genreService.findAll(), Collections.emptyList());
        model.addAttribute(GENRES_ATTRIBUTE_NAME, markedGenres);

        Map<String, Boolean> markedYears = createMarkedIntegerElementsMap(DateUtils.getYears(), Collections.emptyList());
        model.addAttribute(YEARS_ATTRIBUTE_NAME, markedYears);

        Long numberOfFilms = filmService.countFilmsBySearchTerms(new LinkedMultiValueMap<>());
        List<Film> films = filmService.findFilmsBySearchTerms(new LinkedMultiValueMap<>(), DEFAULT_PAGE_NUMBER, calculateNumberOfPages(numberOfFilms));

        return "films";
    }

    @RequestMapping(value = "/films",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFilms(@RequestBody MultiValueMap<String, String> filmSearchAttributes, Model model) {

        Map<Country, Boolean> markedCountries = createMarkedIdentifiableElementsMap(countryService.findAll(),
                convertNull(filmSearchAttributes.get(COUNTRIES_ATTRIBUTE_NAME)));
        model.addAttribute(COUNTRIES_ATTRIBUTE_NAME, markedCountries);

        Map<Genre, Boolean> markedGenres = createMarkedIdentifiableElementsMap(genreService.findAll(),
                convertNull(filmSearchAttributes.get(GENRES_ATTRIBUTE_NAME)));
        model.addAttribute(GENRES_ATTRIBUTE_NAME, markedGenres);

        Map<String, Boolean> markedYears = createMarkedIntegerElementsMap(DateUtils.getYears(),
                convertNull(filmSearchAttributes.get(YEARS_ATTRIBUTE_NAME)));
        model.addAttribute(YEARS_ATTRIBUTE_NAME, markedYears);

        if(!isNullOrEmpty(filmSearchAttributes.get(TITLE_ATTRIBUTE_NAME))) {
            model.addAttribute(TITLE_ATTRIBUTE_NAME, filmSearchAttributes.get(TITLE_ATTRIBUTE_NAME).get(0));
        }

        List<String> ratingRange = filmSearchAttributes.get(RATING_ATTRIBUTE_NAME);
        if(!isNullOrEmpty(ratingRange)) {
            String[][] splittedRatingRange = ratingRange.stream()
                    .filter(s -> s.matches(RATING_RANGE_VALUE_PATTERN))
                    .map(s -> s.split("-"))
                    .toArray(String[][]::new);

            if(splittedRatingRange.length == 2) {
                model.addAttribute(splittedRatingRange[1][0], splittedRatingRange[1][1]);
            }
            model.addAttribute(splittedRatingRange[0][0], splittedRatingRange[0][1]);
        }

        if(!isNullOrEmpty(filmSearchAttributes.get(SORT_BY_ATTRIBUTE_NAME))) {
            model.addAttribute(SORT_BY_ATTRIBUTE_NAME, filmSearchAttributes.get(SORT_BY_ATTRIBUTE_NAME).get(0));
        }

        int pageNumber = DEFAULT_PAGE_NUMBER;
        if(!CollectionUtils.isEmpty(filmSearchAttributes.get(PAGE_ATTRIBUTE_NAME))) {
            pageNumber = Integer.parseInt(filmSearchAttributes.get(PAGE_ATTRIBUTE_NAME).get(0));
        }

        Long numberOfFilms = filmService.countFilmsBySearchTerms(filmSearchAttributes);
        int lastPageNumber = calculateNumberOfPages(numberOfFilms);
        List<Film> filmsBySearchTerms = filmService.findFilmsBySearchTerms(filmSearchAttributes, pageNumber, lastPageNumber);
        Map<Integer, Boolean> paginationRange = getPaginationRange(pageNumber, lastPageNumber);

        model.addAttribute(PAGINATION_RANGE_ATTRIBUTE_NAME, paginationRange);
        model.addAttribute(FILMS_ATTRIBUTE_NAME, filmsBySearchTerms);
        model.addAttribute("", filmsBySearchTerms.stream().findFirst());

        return "films";
    }
}
