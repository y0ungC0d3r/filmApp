package org.student.filmApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.Actor;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.Person;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
	public List<Film> findFilmByFilmDirectors(Person director);
	
	public List<Film> findFilmByFilmActors(Person actor);
}
