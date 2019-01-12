package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.FilmRating;
import org.student.filmApp.entity.PersonRating;
import org.student.filmApp.entity.User;
import org.student.filmApp.service.FilmRatingService;
import org.student.filmApp.service.PersonRatingService;

import java.time.LocalDate;
import java.util.Calendar;

@Controller
public class RatingController {

    @Autowired
    FilmRatingService filmRatingService;

    @Autowired
    PersonRatingService personRatingService;

    @RequestMapping(value = "/change-film-rating", method = RequestMethod.POST)
    String changeFilmRating(@ModelAttribute("filmRating") FilmRating rating) {
        filmRatingService.save(rating);
        return "redirect:/films/" + rating.getFilm().getId();
    }

    @RequestMapping(value = "/change-person-rating", method = RequestMethod.POST)
    String changePersonRating(@ModelAttribute("personRating") PersonRating rating) {
        personRatingService.save(rating);
        return "redirect:/people/" + rating.getPerson().getId();
    }
}
