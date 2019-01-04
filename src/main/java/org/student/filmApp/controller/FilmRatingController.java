package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.FilmRating;
import org.student.filmApp.entity.User;
import org.student.filmApp.service.FilmRatingService;

import java.time.LocalDate;
import java.util.Calendar;

@Controller
public class FilmRatingController {

    @Autowired
    FilmRatingService filmRatingService;

    @RequestMapping(value = "/change-film-rating", method = RequestMethod.POST)
    String changeRating(@ModelAttribute("filmRating") FilmRating rating) {
        filmRatingService.save(rating);
        return "redirect:/films/" + rating.getFilm().getId();
    }
}
