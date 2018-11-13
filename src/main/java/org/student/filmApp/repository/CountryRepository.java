package org.student.filmApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, String> {
	public List<Country> findAll();
}
