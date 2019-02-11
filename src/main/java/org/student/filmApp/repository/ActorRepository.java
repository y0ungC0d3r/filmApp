package org.student.filmApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.student.filmApp.domain.Actor;

public interface ActorRepository extends CrudRepository<Actor, Long> {
	//public List<Actor> findAll();
	
	public List<Actor> findAll();
}
