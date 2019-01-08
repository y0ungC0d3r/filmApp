package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.service.PersonService;

@Controller
public class PersonController {
    @Autowired
    PersonService personService;

    @RequestMapping(value = "/people", method = RequestMethod.GET)
    String showPeople() {
        personService.findPeopleBySearchTerms(null, 1, 1);
        return "people";
    }
}
