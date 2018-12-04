package org.student.filmApp.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(FilmRating.class)
public class FilmRating_ {
    public static volatile SingularAttribute<FilmRating, Short> rating;
    public static volatile SingularAttribute<FilmRating, Date> dateOfRating;
}
