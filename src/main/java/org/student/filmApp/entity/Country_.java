package org.student.filmApp.entity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class Country_ {
    public static volatile SingularAttribute<Country, Long> id;
    public static volatile SingularAttribute<Country, String> name;
    public static volatile SetAttribute<Country, Film> films;
}
