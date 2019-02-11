package org.student.filmApp.domain;

import java.time.LocalDate;
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

@Entity
@Table(name = "person")
public class Person implements Identifiable<Long> {

	public Person() {}

	public Person(Long id) {
		this.id = id;
	}

	@Id
	@Column(name = "id")
	 private Long id;
	
	@Column(name = "full_name")
	 private String fullName;
	
	@Column(name = "stage_name")
	 private String stageName;
	
	@Column(name = "date_of_birth")
	 private LocalDate dateOfBirth;
	
	@Column(name = "date_of_death")
	 private LocalDate dateOfDeath;
	
	@Column(name = "place_of_birth")
	 private String placeOfBirth;
	
	@Column(name = "biography")
	 private String biography;
	
	@Column(name = "height")
	 private Integer height;

	@Column(name = "ratings_sum")
	private Long ratingsSum;

	@Column(name = "number_of_votes")
	private Long numberOfVotes;

	@Column(name = "average_rating")
	private Float averageRating;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "actorId.person")
	private Set<Actor> actorFilmsAssociation;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "actor",
			joinColumns = { @JoinColumn(name = "person_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> actorFilms;
	
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "personRatingId.person")
	private Set<PersonRating> ratings;

	@OneToMany(mappedBy="person")
	private Set<Comment> comments;

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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(LocalDate dateOfDeath) {
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

	public Set<Actor> getActorFilmsAssociation() {
		return actorFilmsAssociation;
	}

	public void setActorFilmsAssociation(Set<Actor> actorFilmsAssociation) {
		this.actorFilmsAssociation = actorFilmsAssociation;
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

	public Set<Film> getMusicianFilms() {
		return musicianFilms;
	}

	public void setMusicianFilms(Set<Film> musicFilms) {
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

	public Long getRatingsSum() {
		return ratingsSum;
	}

	public void setRatingsSum(Long ratingsSum) {
		this.ratingsSum = ratingsSum;
	}

	public Long getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(Long numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public Float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Float averageRating) {
		this.averageRating = averageRating;
	}

	public Set<PersonRating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<PersonRating> ratings) {
		this.ratings = ratings;
	}

	public Set<Film> getActorFilms() {
		return actorFilms;
	}

	public void setActorFilms(Set<Film> actorFilms) {
		this.actorFilms = actorFilms;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}
