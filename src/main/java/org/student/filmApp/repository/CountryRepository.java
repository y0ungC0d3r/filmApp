package org.student.filmApp.repository;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.Country;

@Repository
public interface CountryRepository extends BaseRepository<Country, String> {
	Set<Country> findAll();
}
