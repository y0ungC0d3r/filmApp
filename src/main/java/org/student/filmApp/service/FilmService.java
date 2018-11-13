package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.filmApp.entity.Film;
import org.student.filmApp.repository.FilmRepository;

//@Service
public class FilmService {

    //@Autowired
    FilmRepository filmRepository;

    Film findById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }
}
