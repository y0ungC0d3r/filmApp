package org.student.filmApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.Person;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {
    @Query("SELECT p FROM Person p " +
            "LEFT JOIN FETCH p.actorFilmsAssociation " +
            "LEFT JOIN FETCH p.directorFilms " +
            "LEFT JOIN FETCH p.screenwriterFilms " +
            "LEFT JOIN FETCH p.producerFilms " +
            "LEFT JOIN FETCH p.musicianFilms " +
            "LEFT JOIN FETCH p.editorFilms " +
            "LEFT JOIN FETCH p.cinematographerFilms " +
            "LEFT JOIN FETCH p.costumeDesignerFilms " +
            "LEFT JOIN FETCH p.ratings " +
            "LEFT JOIN FETCH p.comments " +
            "WHERE p.id = :person_id")
    Person findByIdWithFetch(@Param("person_id") Long personId);
}
