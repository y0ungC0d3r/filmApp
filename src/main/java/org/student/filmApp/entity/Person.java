package org.student.filmApp.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "person")
public class Person {
	
	@Id
	@Column(name = "id")
	 private Long id;
	
	@Column(name = "full_name")
	 private String fullName;
	
	@Column(name = "stage_name")
	 private String stageName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	 private Date dateOfBirth;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_death")
	 private Date dateOfDeath;
	
	@Column(name = "place_of_birth")
	 private String placeOfBirth;
	
	@Column(name = "biography")
	 private String biography;
	
	@Column(name = "height")
	 private Integer height;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "actorId.person")
	private Set<Actor> actorFilms;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "director", 
        joinColumns = { @JoinColumn(name = "person_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "film_id") }
    )
	private Set<Film> directorFilms;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "screenwriter",
			joinColumns = { @JoinColumn(name = "person_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> screenwriterFilms;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "producer",
			joinColumns = { @JoinColumn(name = "person_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> producerFilms;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "music",
			joinColumns = { @JoinColumn(name = "person_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> musicianFilms;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "film_editor",
			joinColumns = { @JoinColumn(name = "person_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> editorFilms;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "cinematographer",
			joinColumns = { @JoinColumn(name = "person_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> cinematographerFilms;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "costume_designer",
			joinColumns = { @JoinColumn(name = "person_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> costumeDesignerFilms;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Set<Actor> getActorFilms() {
		return actorFilms;
	}

	public void setActorFilms(Set<Actor> actorFilms) {
		this.actorFilms = actorFilms;
	}

	public Set<Film> getDirectorFilms() {
		return directorFilms;
	}

	public void setDirectorFilms(Set<Film> directorFilms) {
		this.directorFilms = directorFilms;
	}

	public Set<Film> getScreenwriterFilms() {
		return screenwriterFilms;
	}

	public void setScreenwriterFilms(Set<Film> screenwriterFilms) {
		this.screenwriterFilms = screenwriterFilms;
	}

	public Set<Film> getProducerFilms() {
		return producerFilms;
	}

	public void setProducerFilms(Set<Film> producerFilms) {
		this.producerFilms = producerFilms;
	}

	public Set<Film> getMusicFilms() {
		return musicianFilms;
	}

	public void setMusicFilms(Set<Film> musicFilms) {
		this.musicianFilms = musicFilms;
	}

	public Set<Film> getEditorFilms() {
		return editorFilms;
	}

	public void setEditorFilms(Set<Film> editorFilms) {
		this.editorFilms = editorFilms;
	}

	public Set<Film> getCinematographerFilms() {
		return cinematographerFilms;
	}

	public void setCinematographerFilms(Set<Film> cinematographerFilms) {
		this.cinematographerFilms = cinematographerFilms;
	}

	public Set<Film> getCostumeDesignerFilms() {
		return costumeDesignerFilms;
	}

	public void setCostumeDesignerFilms(Set<Film> costumeDesignerFilms) {
		this.costumeDesignerFilms = costumeDesignerFilms;
	}
}
