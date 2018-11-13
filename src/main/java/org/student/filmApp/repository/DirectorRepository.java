package org.student.filmApp.repository;

import org.springframework.data.repository.CrudRepository;
import org.student.filmApp.entity.Actor;

public interface DirectorRepository extends CrudRepository<Actor, Long> {
	
}
