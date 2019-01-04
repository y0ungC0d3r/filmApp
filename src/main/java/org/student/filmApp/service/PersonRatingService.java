package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.student.filmApp.entity.PersonRating;
import org.student.filmApp.repository.PersonRatingRepository;

import java.time.LocalDate;

@Service
public class PersonRatingService {

    @Autowired
    PersonRatingRepository personRatingRepository;

    @Transactional
    public void save(PersonRating personRating) {
        personRating.setDateOfRating(LocalDate.now());
        personRatingRepository.save(personRating);
    }
}
