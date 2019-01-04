package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.entity.*;
import org.student.filmApp.service.*;
import org.student.filmApp.utils.DateUtils;
import org.student.filmApp.utils.ImageUtils;

import javax.servlet.ServletContext;
import java.util.*;
import java.util.function.BiFunction;

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

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    ServletContext servletContext;

    @Autowired
    ResourceLoader resourceLoader;

    public static final String RATING_RANGE_VALUE_PATTERN = "(floor|roof)-[1-5]";

    @RequestMapping(value = { "/", "/films"}, method = RequestMethod.GET)
    String showFilms(Model model) {

        Map<Country, Boolean> markedCountries = createMarkedIdentifiableElementsMap(countryService.findAll(), Collections.emptyList());
        model.addAttribute(COUNTRIES_ATTRIBUTE_NAME, markedCountries);

        Map<Genre, Boolean> markedGenres = createMarkedIdentifiableElementsMap(genreService.findAll(), Collections.emptyList());
        model.addAttribute(GENRES_ATTRIBUTE_NAME, markedGenres);

        Map<String, Boolean> markedYears = createMarkedIntegerElementsMap(DateUtils.getYears(), Collections.emptyList());
        model.addAttribute(YEARS_ATTRIBUTE_NAME, markedYears);

        Long numberOfFilms = filmService.countFilmsBySearchTerms(new LinkedMultiValueMap<>());
        int lastPageNumber = calculateNumberOfPages(numberOfFilms);

        model.addAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, DEFAULT_PAGE_NUMBER);

        List<Film> films = filmService.findFilmsBySearchTerms(new LinkedMultiValueMap<>(), DEFAULT_PAGE_NUMBER, lastPageNumber);
        List<Integer> paginationRange = getPaginationRange(DEFAULT_PAGE_NUMBER, lastPageNumber);

        model.addAttribute(PAGINATION_RANGE_ATTRIBUTE_NAME, paginationRange);
        model.addAttribute(FILMS_ATTRIBUTE_NAME, films);

        return FILMS_VIEW_NAME;
    }

    @RequestMapping(value = "/films",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFilms(@RequestBody MultiValueMap<String, String> filmSearchAttributes, Model model) {

        Map<Country, Boolean> markedCountries = createMarkedIdentifiableElementsMap(countryService.findAll(),
                emptyIfNull(filmSearchAttributes.get(COUNTRIES_ATTRIBUTE_NAME)));
        model.addAttribute(COUNTRIES_ATTRIBUTE_NAME, markedCountries);

        Map<Genre, Boolean> markedGenres = createMarkedIdentifiableElementsMap(genreService.findAll(),
                emptyIfNull(filmSearchAttributes.get(GENRES_ATTRIBUTE_NAME)));
        model.addAttribute(GENRES_ATTRIBUTE_NAME, markedGenres);

        Map<String, Boolean> markedYears = createMarkedIntegerElementsMap(DateUtils.getYears(),
                emptyIfNull(filmSearchAttributes.get(YEARS_ATTRIBUTE_NAME)));
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

        Long numberOfFilms = filmService.countFilmsBySearchTerms(filmSearchAttributes);
        int lastPageNumber = calculateNumberOfPages(numberOfFilms);
        int pageNumber = calculateCurrPageNumber(filmSearchAttributes.get(CURRENT_PAGE_ATTRIBUTE_NAME), lastPageNumber);

        model.addAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, pageNumber);

        List<Film> filmsBySearchTerms = filmService.findFilmsBySearchTerms(filmSearchAttributes, pageNumber, lastPageNumber);
        List<Integer> paginationRange = getPaginationRange(pageNumber, lastPageNumber);

        model.addAttribute(PAGINATION_RANGE_ATTRIBUTE_NAME, paginationRange);
        model.addAttribute(FILMS_ATTRIBUTE_NAME, filmsBySearchTerms);

        return FILMS_VIEW_NAME;
    }

    @RequestMapping(value = "/films/{id}", method = RequestMethod.GET)
    String showFilm(@PathVariable String id, Model model) throws IllegalAccessException {

        Optional<User> loggedUser = userService.findByUsername(securityService.findLoggedInUsername());

        Long filmId = Long.valueOf(id);
        Long userId = loggedUser
                .map(User::getId)
                .orElseThrow(IllegalAccessException::new);

        Film film = filmService.findByIdWithFetch(filmId);

        model.addAttribute(FILM_ATTRIBUTE_NAME, film);

        FilmRating filmRating = film
                .getRatings()
                .stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .findFirst()
                .orElse(new FilmRating(new Film(filmId), new User(userId)));

        model.addAttribute(FILM_RATING_ATTRIBUTE_NAME, filmRating);

        Map<String, String> imagesPaths = ImageUtils.getAllFilmImagePaths(filmId);

        model.addAttribute(IMAGES_PATHS_ATTRIBUTE_NAME, imagesPaths);

        return FILM_VIEW_NAME;
    }
}
