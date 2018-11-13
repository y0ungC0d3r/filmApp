package org.student.filmApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.student.filmApp.entity.Actor;
import org.student.filmApp.entity.ActorId;
import org.student.filmApp.entity.Person;

public interface ActorRepository extends CrudRepository<Actor, Long> {
	//public List<Actor> findAll();
	
	public List<Actor> findAll();
}
