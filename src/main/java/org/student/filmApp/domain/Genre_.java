package org.student.filmApp.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Genre.class)
public class Genre_ {
    public static volatile SingularAttribute<Genre, Long> id;
    public static volatile SingularAttribute<Genre, String> name;
    public static volatile SetAttribute<Genre, Film> films;
}
