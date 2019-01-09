package org.student.filmApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.Person;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {
    // TODO
    /*@Query("SELECT p FROM Person p " +
            "LEFT JOIN FETCH p.countries " +
            "LEFT JOIN FETCH p.genres " +
            "LEFT JOIN FETCH p.filmActors " +
            "LEFT JOIN FETCH p.filmCinematographers " +
            "LEFT JOIN FETCH p.filmDirectors " +
            "LEFT JOIN FETCH p.filmEditors " +
            "LEFT JOIN FETCH p.filmMusicians " +
            "LEFT JOIN FETCH p.filmProducers " +
            "LEFT JOIN FETCH p.filmScreenwriters " +
            "LEFT JOIN FETCH p.ratings " +
            "WHERE f.id = :film_id" +
            "ORDER BY f.worldwideReleaseDate")
    Person findByIdWithFetch(@Param("person_id") Long personId);*/
}
