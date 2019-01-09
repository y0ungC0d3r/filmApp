package org.student.filmApp.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;

public class Person_ {
    public static volatile SingularAttribute<Person, Long> id;
    public static volatile SingularAttribute<Person, String> fullName;
    public static volatile SingularAttribute<Person, String> stageName;
    public static volatile SingularAttribute<Person, LocalDate> dateOfBirth;
    public static volatile SingularAttribute<Person, LocalDate> dateOfDeath;
    public static volatile SingularAttribute<Person, String> placeOfBirth;
    public static volatile SingularAttribute<Person, String> biography;
    public static volatile SingularAttribute<Person, Integer> height;
    public static volatile SingularAttribute<Person, Long> ratingsSum;
    public static volatile SingularAttribute<Person, Long> numberOfVotes;
    public static volatile SingularAttribute<Person, Float> averageRating;
    public static volatile SetAttribute<Actor, Film> actorFilms;
    public static volatile SetAttribute<Person, Film> directorFilms;
    public static volatile SetAttribute<Person, Film> screenwriterFilms;
    public static volatile SetAttribute<Person, Film> producerFilms;
    public static volatile SetAttribute<Person, Film> musicianFilms;
    public static volatile SetAttribute<Person, Film> editorFilms;
    public static volatile SetAttribute<Person, Film> cinematographerFilms;
    public static volatile SetAttribute<Person, Film> costumeDesignerFilms;
}
