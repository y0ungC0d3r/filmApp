package org.student.filmApp.utils;

import org.student.filmApp.ProfessionName;
import org.student.filmApp.entity.*;

import javax.persistence.metamodel.SetAttribute;

public class PersonUtils {
    public static SetAttribute<Film, Person> mapProfession(ProfessionName professionName) {
        switch(professionName) {
            case DIRECTOR:
                return Film_.filmDirectors;
            case SCREENWRITER:
                return Film_.filmScreenwriters;
            case CINEMATOGRAPHER:
                return Film_.filmCinematographers;
            case EDITOR:
                return Film_.filmEditors;
            case PRODUCER:
                return Film_.filmProducers;
            case MUSICIAN:
                return Film_.filmMusicians;
            default:
                throw new IllegalArgumentException();
        }
    }
}
