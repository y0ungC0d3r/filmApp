package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.student.filmApp.entity.Country;
import org.student.filmApp.repository.CountryRepository;

import java.util.Set;

@Service
public class CountryService {
    @Autowired
    CountryRepository countryRepository;

    public Set<Country> findAll() {
        return countryRepository.findAll();
    }
}
