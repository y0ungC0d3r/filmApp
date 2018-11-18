package org.student.filmApp.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Film.class)
public class Film_ {
    public static volatile SingularAttribute<Film, Long> id;
    public static volatile SingularAttribute<Film, String> originalTitle;
    public static volatile SingularAttribute<Film, String> polishTitle;
    public static volatile SingularAttribute<Film, Date> worldwideReleaseDate;
    public static volatile SingularAttribute<Film, Date> polishReleaseDate;
    public static volatile SingularAttribute<Film, Integer> budget;
    public static volatile SingularAttribute<Film, Integer> boxOffice;
    public static volatile SingularAttribute<Film, Integer> runningTime;
    public static volatile SetAttribute<Film, Actor> filmActors;
    public static volatile SetAttribute<Film, Person> filmDirectors;
    public static volatile SetAttribute<Film, Person> filmScreenwriters;
    public static volatile SetAttribute<Film, Person> filmProducers;
    public static volatile SetAttribute<Film, Person> filmMusicians;
    public static volatile SetAttribute<Film, Person> filmEditors;
    public static volatile SetAttribute<Film, Person> filmCinematographers;
    public static volatile SetAttribute<Film, Genre> genres;
}
