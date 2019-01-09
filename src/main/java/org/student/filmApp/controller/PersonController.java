package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.Consts;
import org.student.filmApp.entity.Person;
import org.student.filmApp.service.PersonService;

import java.util.List;

import static org.student.filmApp.Consts.*;
import static org.student.filmApp.utils.PaginationUtils.*;

@Controller
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping(value = "/people", method = RequestMethod.GET)
    String showPeople(Model model) {

        Long numberOfFilms = personService.countPeopleBySearchTerms(new LinkedMultiValueMap<>());
        int lastPageNumber = calculateNumberOfPages(numberOfFilms);

        model.addAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, DEFAULT_PAGE_NUMBER);

        List<Person> people = personService.findPeopleBySearchTerms(new LinkedMultiValueMap<>(), Consts.DEFAULT_PAGE_NUMBER, lastPageNumber);
        List<Integer> paginationRange = getPaginationRange(DEFAULT_PAGE_NUMBER, lastPageNumber);

        model.addAttribute(PAGINATION_RANGE_ATTRIBUTE_NAME, paginationRange);
        model.addAttribute(PEOPLE_ATTRIBUTE_NAME, people);

        return PEOPLE_VIEW_NAME;
    }

    @RequestMapping(value = "/people",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String searchPeople(@RequestBody MultiValueMap<String, String> personSearchAttributes, Model model) {

        Long numberOfFilms = personService.countPeopleBySearchTerms(personSearchAttributes);
        int lastPageNumber = calculateNumberOfPages(numberOfFilms);
        int pageNumber = calculateCurrPageNumber(personSearchAttributes.get(CURRENT_PAGE_ATTRIBUTE_NAME), lastPageNumber);

        model.addAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, pageNumber);

        List<Person> peopleBySearchTerms = personService.findPeopleBySearchTerms(personSearchAttributes, pageNumber, lastPageNumber);
        List<Integer> paginationRange = getPaginationRange(pageNumber, lastPageNumber);

        model.addAttribute(PAGINATION_RANGE_ATTRIBUTE_NAME, paginationRange);
        model.addAttribute(FILMS_ATTRIBUTE_NAME, peopleBySearchTerms);

        return PEOPLE_VIEW_NAME;
    }
}
