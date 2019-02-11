package org.student.filmApp.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(Film.class)
public class Film_ {
    public static volatile SingularAttribute<Film, Long> id;
    public static volatile SingularAttribute<Film, String> originalTitle;
    public static volatile SingularAttribute<Film, String> polishTitle;
    public static volatile SingularAttribute<Film, LocalDate> worldwideReleaseDate;
    public static volatile SingularAttribute<Film, LocalDate> polishReleaseDate;
    public static volatile SingularAttribute<Film, Integer> budget;
    public static volatile SingularAttribute<Film, Integer> boxOffice;
    public static volatile SingularAttribute<Film, Integer> runningTime;
    public static volatile SingularAttribute<Film, Long> ratingsSum;
    public static volatile SingularAttribute<Film, Long> numberOfVotes;
    public static volatile SingularAttribute<Film, Float> averageRating;
    public static volatile SingularAttribute<Film, Actor> filmActorsAssociation;
    public static volatile SetAttribute<Film, Person> filmActors;
    public static volatile SetAttribute<Film, Person> filmDirectors;
    public static volatile SetAttribute<Film, Person> filmScreenwriters;
    public static volatile SetAttribute<Film, Person> filmProducers;
    public static volatile SetAttribute<Film, Person> filmMusicians;
    public static volatile SetAttribute<Film, Person> filmEditors;
    public static volatile SetAttribute<Film, Person> filmCinematographers;
    public static volatile SetAttribute<Film, Genre> genres;
    public static volatile SetAttribute<Film, Country> countries;
    public static volatile SetAttribute<Film, FilmRating> ratings;
}
