package org.student.filmApp.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "film")
public class Film implements Identifiable<Long> {
	
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "original_title")
	private String originalTitle;

	@Column(name = "polish_title")
	private String polishTitle;

	@Column(name = "worldwide_release_date")
	private LocalDate worldwideReleaseDate;

	@Column(name = "polish_release_date")
	private LocalDate polishReleaseDate;

	@Column(name = "budget")
	private Integer budget;

	@Column(name = "box_office")
	private Integer boxOffice;

	@Column(name = "running_time")
	private Integer runningTime;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "actorId.film")
	private Set<Actor> filmActors;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "directorFilms")
	private Set<Person> filmDirectors;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "screenwriterFilms")
	private Set<Person> filmScreenwriters;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "producerFilms")
	private Set<Person> filmProducers;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "musicianFilms")
	private Set<Person> filmMusicians;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "editorFilms")
	private Set<Person> filmEditors;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "cinematographerFilms")
	private Set<Person> filmCinematographers;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "films")
	private Set<Genre> genres;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "films")
	private Set<Country> countries;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "filmRatingId.film")
	private Set<FilmRating> filmRatings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getPolishTitle() {
		return polishTitle;
	}

	public void setPolishTitle(String polishTitle) {
		this.polishTitle = polishTitle;
	}

	public LocalDate getWorldwideReleaseDate() {
		return worldwideReleaseDate;
	}

	public void setWorldwideReleaseDate(LocalDate worldwideReleaseDate) {
		this.worldwideReleaseDate = worldwideReleaseDate;
	}

	public LocalDate getPolishReleaseDate() {
		return polishReleaseDate;
	}

	public void setPolishReleaseDate(LocalDate polishReleaseDate) {
		this.polishReleaseDate = polishReleaseDate;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Integer getBoxOffice() {
		return boxOffice;
	}

	public void setBoxOffice(Integer boxOffice) {
		this.boxOffice = boxOffice;
	}

	public Integer getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Integer runningTime) {
		this.runningTime = runningTime;
	}

	public Set<Actor> getFilmActors() {
		return filmActors;
	}

	public void setFilmActors(Set<Actor> filmActors) {
		this.filmActors = filmActors;
	}

	public Set<Person> getFilmDirectors() {
		return filmDirectors;
	}

	public void setFilmDirectors(Set<Person> filmDirectors) {
		this.filmDirectors = filmDirectors;
	}

	public Set<Person> getFilmScreenwriters() {
		return filmScreenwriters;
	}

	public void setFilmScreenwriters(Set<Person> filmScreenwriters) {
		this.filmScreenwriters = filmScreenwriters;
	}

	public Set<Person> getFilmProducers() {
		return filmProducers;
	}

	public void setFilmProducers(Set<Person> filmProducers) {
		this.filmProducers = filmProducers;
	}

	public Set<Person> getFilmMusicians() {
		return filmMusicians;
	}

	public void setFilmMusicians(Set<Person> filmMusicians) {
		this.filmMusicians = filmMusicians;
	}

	public Set<Person> getFilmEditors() {
		return filmEditors;
	}

	public void setFilmEditors(Set<Person> filmEditors) {
		this.filmEditors = filmEditors;
	}

	public Set<Person> getFilmCinematographers() {
		return filmCinematographers;
	}

	public void setFilmCinematographers(Set<Person> filmCinematographers) {
		this.filmCinematographers = filmCinematographers;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public Set<Country> getCountries() {
		return countries;
	}

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}

	public Set<FilmRating> getFilmRatings() {
		return filmRatings;
	}

	public void setFilmRatings(Set<FilmRating> filmRatings) {
		this.filmRatings = filmRatings;
	}
}
