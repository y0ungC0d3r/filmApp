package org.student.filmApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.student.filmApp.entity.Actor;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.Person;

public interface FilmRepository extends CrudRepository<Film, Long> {
	public List<Film> findFilmByFilmDirectors(Person director);
	
	public List<Film> findFilmByFilmActors(Person actor);
}
