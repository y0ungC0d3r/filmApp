package org.student.filmApp.utils;

import org.student.filmApp.entity.*;

import javax.persistence.metamodel.SetAttribute;

public class PersonUtils {
    public static SetAttribute<Film, Person> mapProfession(String professionName) {
        switch(professionName) {
            case "actor":
                return null;
            case "director":
                return Film_.filmDirectors;
            case "screenwriter":
                return Film_.filmScreenwriters;
            case "cinematographer":
                return Film_.filmCinematographers;
            case "editor":
                return Film_.filmEditors;
            case "producer":
                return Film_.filmProducers;
            case "musician":
                return Film_.filmMusicians;
            default:
                throw new IllegalArgumentException();
        }
    }
}
