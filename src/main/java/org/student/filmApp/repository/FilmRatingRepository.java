package org.student.filmApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.FilmRating;

import java.util.Set;

@Repository
public interface FilmRatingRepository extends BaseRepository<FilmRating, Long> {

    @Query("SELECT fr FROM FilmRating fr " +
            "WHERE fr.filmRatingId.user.id = :userId")
    Set<FilmRating> findByUserId(@Param("userId") Long userId);

    @Query("SELECT fr FROM FilmRating fr " +
            "WHERE fr.filmRatingId.user.username = :username")
    Set<FilmRating> findByUsername(@Param("username") String username);
}
