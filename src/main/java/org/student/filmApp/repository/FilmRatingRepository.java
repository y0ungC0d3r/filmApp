package org.student.filmApp.repository;

import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.FilmRating;

@Repository
public interface FilmRatingRepository extends BaseRepository<FilmRating, Long> {
}
