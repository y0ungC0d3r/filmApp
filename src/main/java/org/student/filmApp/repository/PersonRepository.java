package org.student.filmApp.repository;

import org.springframework.stereotype.Repository;
import org.student.filmApp.entity.Person;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {
}
