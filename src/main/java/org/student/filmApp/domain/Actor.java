package org.student.filmApp.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "actor")
@AssociationOverrides({
	@AssociationOverride(name = "actorId.person",
		joinColumns = @JoinColumn(name = "person_id")),
	@AssociationOverride(name = "actorId.film",
		joinColumns = @JoinColumn(name = "film_id")) })
public class Actor {
	
	@EmbeddedId
	ActorId actorId = new ActorId();
	
	@Column(name = "character")
	private String character;

	public ActorId getActorId() {
		return actorId;
	}

	public void setActorId(ActorId actorId) {
		this.actorId = actorId;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}
	
	public Person getPerson() {
		return actorId.getPerson();
	}
	
	@Transient
	public void setPerson(Person person) {
		actorId.setPerson(person);
	}
	
	public Film getFilm() {
		return actorId.getFilm();
	}
	
	@Transient
	public void setFilm(Film Film) {
		actorId.setFilm(Film);
	}
	
}
