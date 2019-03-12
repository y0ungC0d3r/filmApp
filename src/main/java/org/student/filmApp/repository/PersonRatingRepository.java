package org.student.filmApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.student.filmApp.domain.PersonRating;

import java.util.Set;

@Repository
public interface PersonRatingRepository extends CrudRepository<PersonRating, Long> {

    @Query("SELECT pr FROM PersonRating pr " +
            "WHERE pr.personRatingId.user.id = :userId")
    Set<PersonRating> findByUserId(@Param("userId") Long userId);

    @Query("SELECT pr FROM PersonRating pr " +
            "WHERE pr.personRatingId.user.username = :username")
    Set<PersonRating> findByUsername(@Param("username") String username);
}
