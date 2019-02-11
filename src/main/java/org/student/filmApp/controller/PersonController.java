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
import org.student.filmApp.Consts;
import org.student.filmApp.domain.Person;
import org.student.filmApp.domain.PersonRating;
import org.student.filmApp.domain.Profession;
import org.student.filmApp.domain.User;
import org.student.filmApp.service.PersonService;
import org.student.filmApp.service.UserServiceImpl;
import org.student.filmApp.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.student.filmApp.Consts.*;
import static org.student.filmApp.utils.CollectionUtils.createMarkedIdentifiableElementsMap;
import static org.student.filmApp.utils.CollectionUtils.emptyIfNull;
import static org.student.filmApp.utils.CollectionUtils.isNullOrEmpty;
import static org.student.filmApp.utils.PaginationUtils.*;

@Controller
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping(value = "/people", method = RequestMethod.GET)
    String showPeople(Model model) {

        Map<Profession, Boolean> markedProfessions = createMarkedIdentifiableElementsMap(Profession.getAllprofessions(),
                Collections.emptyList());
        model.addAttribute(PROFESSION_ATTRIBUTE_NAME, markedProfessions);

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

        Map<Profession, Boolean> markedProfessions = createMarkedIdentifiableElementsMap(Profession.getAllprofessions(),
                emptyIfNull(personSearchAttributes.get(PROFESSION_ATTRIBUTE_NAME)));
        model.addAttribute(PROFESSION_ATTRIBUTE_NAME, markedProfessions);

        if(!isNullOrEmpty(personSearchAttributes.get(PERSON_NAME_ATTRIBUTE_NAME))) {
            model.addAttribute(PERSON_NAME_ATTRIBUTE_NAME, personSearchAttributes.get(PERSON_NAME_ATTRIBUTE_NAME).get(0));
        }

        Long numberOfFilms = personService.countPeopleBySearchTerms(personSearchAttributes);
        int lastPageNumber = calculateNumberOfPages(numberOfFilms);
        int pageNumber = calculateCurrPageNumber(personSearchAttributes.get(CURRENT_PAGE_ATTRIBUTE_NAME), lastPageNumber);

        model.addAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, pageNumber);

        List<Person> peopleBySearchTerms = personService.findPeopleBySearchTerms(personSearchAttributes, pageNumber, lastPageNumber);
        List<Integer> paginationRange = getPaginationRange(pageNumber, lastPageNumber);

        model.addAttribute(PAGINATION_RANGE_ATTRIBUTE_NAME, paginationRange);
        model.addAttribute(PEOPLE_ATTRIBUTE_NAME, peopleBySearchTerms);

        return PEOPLE_VIEW_NAME;
    }

    @RequestMapping(value = "/people/{id}", method = RequestMethod.GET)
    String showFilm(@PathVariable String id, Model model) throws IllegalAccessException, IOException {

        Long personId = Long.valueOf(id);

        User user = userService.findLoggedUser();

        Person person = personService.findByIdWithFetch(personId);

        model.addAttribute(PERSON_ATTRIBUTE_NAME, person);

        PersonRating personRating = person.getRatings()
                .stream()
                .filter(r -> r.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElse(new PersonRating(new Person(personId), user));

        model.addAttribute(PERSON_RATING_ATTRIBUTE_NAME, personRating);

        File personImagesFileDir = resourceLoader.getResource(PEOPLE_IMAGES_PATH + personId).getFile();
        File thumbnailsFileDir = resourceLoader.getResource(PEOPLE_IMAGES_PATH + personId + "/thumbnail").getFile();
        Map<String, String> imagesPaths = ImageUtils.getAllFilmImagePaths(personId, personImagesFileDir, thumbnailsFileDir, PEOPLE_IMAGES_PATH);

        String posterPath = ImageUtils.getPosterPath(personId, personImagesFileDir, PEOPLE_IMAGES_PATH);

        model.addAttribute(IMAGES_PATHS_ATTRIBUTE_NAME, imagesPaths);
        model.addAttribute(POSTER_PATH_ATTRIBUTE_NAME, posterPath);

        return PERSON_VIEW_NAME;
    }
}
