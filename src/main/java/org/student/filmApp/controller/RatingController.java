package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.FilmRating;
import org.student.filmApp.entity.PersonRating;
import org.student.filmApp.entity.User;
import org.student.filmApp.service.FilmRatingService;
import org.student.filmApp.service.PersonRatingService;
import org.student.filmApp.service.SecurityServiceImpl;
import org.student.filmApp.service.UserServiceImpl;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import static org.student.filmApp.Consts.*;

@Controller
public class RatingController {

    @Autowired
    FilmRatingService filmRatingService;

    @Autowired
    PersonRatingService personRatingService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

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

    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    String getRatings(@RequestParam(value = "rating-category", required = false) String ratingCat, Model model) throws IllegalAccessException {

        Optional<User> loggedUser = userService.findByUsername(securityService.findLoggedInUsername());

        Long userId = loggedUser
                .map(User::getId)
                .orElseThrow(IllegalAccessException::new);

        if(RATING_CATEGORY_PEOPLE_ATTRIBUTE_VALUE.equals(ratingCat)) {
            model.addAttribute(PEOPLE_RATINGS_ATTRIBUTE_NAME, personRatingService.findByUserId(userId));
        } else {
            model.addAttribute(FILMS_RATINGS_ATTRIBUTE_NAME, filmRatingService.findByUserId(userId));
        }

        return RATINGS_VIEW_NAME;
    }
}
