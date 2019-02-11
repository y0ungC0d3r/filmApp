package org.student.filmApp.controller;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.student.filmApp.domain.FilmRating;
import org.student.filmApp.domain.PersonRating;
import org.student.filmApp.service.FilmRatingService;
import org.student.filmApp.service.PersonRatingService;
import org.student.filmApp.service.UserServiceImpl;

import static org.student.filmApp.Consts.*;

@Controller
public class RatingController {

    @Autowired
    FilmRatingService filmRatingService;

    @Autowired
    PersonRatingService personRatingService;

    @Autowired
    private UserServiceImpl userService;

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
    String getRatings(@RequestParam(value = "rating-category", required = false) String ratingCat,
                      @RequestParam(value = "username", required = false) String username,
                      Model model) throws IllegalAccessException {

        if(Strings.isNullOrEmpty(username)) {
            username = userService.findLoggedUser().getUsername();
        }

        model.addAttribute(USERNAME_ATTRIBUTE_NAME, username);

        if(RATING_CATEGORY_PEOPLE_ATTRIBUTE_VALUE.equals(ratingCat)) {
            model.addAttribute(PEOPLE_RATINGS_ATTRIBUTE_NAME, personRatingService.findByUsername(username));
        } else {
            model.addAttribute(FILMS_RATINGS_ATTRIBUTE_NAME, filmRatingService.findByUsername(username));
        }

        return RATINGS_VIEW_NAME;
    }
}
