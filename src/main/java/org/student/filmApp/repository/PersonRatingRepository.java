package org.student.filmApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.PersonRating;

import java.util.Set;

@Repository
public interface PersonRatingRepository extends BaseRepository<PersonRating, Long> {

    @Query("SELECT pr FROM PersonRating pr " +
            "WHERE pr.personRatingId.user.id = :userId")
    Set<PersonRating> findByUserId(@Param("userId") Long userId);
}
