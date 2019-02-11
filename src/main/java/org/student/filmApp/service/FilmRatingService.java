package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.student.filmApp.domain.FilmRating;
import org.student.filmApp.repository.FilmRatingRepository;

import java.time.LocalDate;
import java.util.Set;

@Service
public class FilmRatingService {

    @Autowired
    FilmRatingRepository filmRatingRepository;

    @Transactional
    public void save(FilmRating filmRating) {
        filmRating.setDateOfRating(LocalDate.now());
        filmRatingRepository.save(filmRating);
    }

    public Set<FilmRating> findByUserId(Long userId) {
        return filmRatingRepository.findByUserId(userId);
    }

    public Set<FilmRating> findByUsername(String username) {
        return filmRatingRepository.findByUsername(username);
    }
}
