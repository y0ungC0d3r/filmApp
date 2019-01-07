package org.student.filmApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {
    @RequestMapping(value = "/people", method = RequestMethod.GET)
    String showPeople() {

        return "people";
    }
}
