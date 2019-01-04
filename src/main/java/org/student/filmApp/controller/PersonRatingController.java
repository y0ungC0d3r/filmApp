package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.entity.PersonRating;
import org.student.filmApp.service.PersonRatingService;

@Controller
public class PersonRatingController {

    @Autowired
    PersonRatingService personRatingService;

    @RequestMapping(value = "/change-person-rating", method = RequestMethod.POST)
    String changeRating(@ModelAttribute("personRating") PersonRating rating) {
        personRatingService.save(rating);
        return "redirect:/people/" + rating.getPerson().getId();
    }
}
