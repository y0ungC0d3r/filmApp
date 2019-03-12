package org.student.filmApp.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, String> {
	Set<Country> findAll();
}
