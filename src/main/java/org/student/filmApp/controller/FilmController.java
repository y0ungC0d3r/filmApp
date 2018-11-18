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
import org.student.filmApp.service.GenreService;
import org.student.filmApp.utils.DateUtils;

import java.util.Set;

@Controller
public class FilmController {

    @Autowired
    CountryService countryService;

    @Autowired
    GenreService genreService;

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    String showFilms(Model model) {
        Set<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);

        Set<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        model.addAttribute("years", DateUtils.getYears());

        return "films";
    }

    @RequestMapping(value = "/films",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchFilms(@RequestBody MultiValueMap<String, String> filmSearchCriteria, Model model) {
        filmSearchCriteria.forEach((k, mv) -> mv.forEach(v -> System.out.println(k + "  " + v)));
        return "films";
    }

}
