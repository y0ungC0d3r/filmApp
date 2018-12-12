package org.student.filmApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.Film;

@Repository
public interface FilmRepository extends BaseRepository<Film, Long> {

	@Query("SELECT f FROM Film f " +
			"JOIN FETCH f.countries " +
			"JOIN FETCH f.genres " +
			"JOIN FETCH f.filmActors " +
			"JOIN FETCH f.filmCinematographers " +
			"JOIN FETCH f.filmDirectors " +
			"JOIN FETCH f.filmEditors " +
			"JOIN FETCH f.filmMusicians " +
			"JOIN FETCH f.filmProducers " +
			"JOIN FETCH f.filmScreenwriters " +
			"WHERE f.id = :id")
	Film findByIdWithFetch(@Param("id") Long id);
}