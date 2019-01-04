package org.student.filmApp.repository;

import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.PersonRating;

@Repository
public interface PersonRatingRepository extends BaseRepository<PersonRating, Long> {
}
