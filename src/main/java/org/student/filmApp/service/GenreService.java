package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.filmApp.domain.Genre;
import org.student.filmApp.repository.GenreRepository;

import java.util.Set;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public Set<Genre> findAll() {
        return genreRepository.findAll();
    }

}
