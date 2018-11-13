package org.student.filmApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
	public List<Person> findAll();
	
	public List<Person> findPersonByDirectorFilms(Film directorFilms);
	
	@Query("SELECT p FROM Person p JOIN FETCH p.directorFilms")
	public List<Person> findPersonAndDirectorFilms();

	/*@Query("SELECT p FROM Person p JOIN FETCH p.directorFilms")
	public List<Person> findPersonAndDirectorFilms();*/
	
	/*@Query("SELECT p FROM Person p JOIN FETCH p.directorFilms WHERE p.id = ?1")
	public Person findPersonAndDirectorFilmsByPersonId(Long Id);*/
}
