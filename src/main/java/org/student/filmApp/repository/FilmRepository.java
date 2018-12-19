package org.student.filmApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.student.filmApp.entity.Film;

@Repository
public interface FilmRepository extends BaseRepository<Film, Long> {

	@Query("SELECT f FROM Film f " +
			"LEFT JOIN FETCH f.countries " +
			"LEFT JOIN FETCH f.genres " +
			"LEFT JOIN FETCH f.filmActors " +
			"LEFT JOIN FETCH f.filmCinematographers " +
			"LEFT JOIN FETCH f.filmDirectors " +
			"LEFT JOIN FETCH f.filmEditors " +
			"LEFT JOIN FETCH f.filmMusicians " +
			"LEFT JOIN FETCH f.filmProducers " +
			"LEFT JOIN FETCH f.filmScreenwriters " +
			"LEFT JOIN FETCH f.ratings " +
			"WHERE f.id = :film_id")
	Film findByIdWithFetch(@Param("film_id") Long filmId);
}