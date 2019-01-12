package org.student.filmApp;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Map;

public class Consts {



    private Consts() {}

    public static final String START_VIEW_NAME = "start";
    public static final String FILM_VIEW_NAME = "film";
    public static final String FILMS_VIEW_NAME = "films";
    public static final String PERSON_VIEW_NAME = "person";
    public static final String PEOPLE_VIEW_NAME = "people";

    public static final String FILM_RATING_ATTRIBUTE_NAME = "filmRating";
    public static final String PERSON_RATING_ATTRIBUTE_NAME = "personRating";
    public static final String FILM_COUNTRIES_ATTRIBUTE_NAME = "countries";
    public static final String FILM_GENRES_ATTRIBUTE_NAME = "genres";
    public static final String YEARS_ATTRIBUTE_NAME = "years";
    public static final String FILM_TITLE_ATTRIBUTE_NAME = "title";
    public static final String PERSON_NAME_ATTRIBUTE_NAME = "name";
    public static final String SORT_BY_ATTRIBUTE_NAME = "sort_by";
    public static final String RATING_ATTRIBUTE_NAME = "rating";
    public static final String CURRENT_PAGE_ATTRIBUTE_NAME = "page";
    public static final String PAGINATION_RANGE_ATTRIBUTE_NAME = "pagination";
    public static final String FILMS_ATTRIBUTE_NAME = "films";
    public static final String FILM_ATTRIBUTE_NAME = "film";
    public static final String PEOPLE_ATTRIBUTE_NAME = "people";
    public static final String PERSON_ATTRIBUTE_NAME = "person";
    public static final String IMAGES_PATHS_ATTRIBUTE_NAME = "imagesPaths";
    public static final String POSTER_PATH_ATTRIBUTE_NAME = "posterPath";
    public static final String POSTER_PATHS_ATTRIBUTE_NAME = "posterPaths";
    public static final String PROFESSION_ATTRIBUTE_NAME = "professions";

    public static final String SORT_BY_CRITERION_NAME = "sort_by";
    public static final String RATING_CRITERION_NAME = "rating";
    public static final String FILM_GENRE_CRITERION_NAME = "genres";
    public static final String FILM_COUNTRY_CRITERION_NAME = "countries";
    public static final String FILM_TITLE_CRITERION_NAME = "title";
    public static final String YEARS_CRITERION_NAME = "years";
    public static final String PAGE_CRITERION_NAME = "page";
    public static final String PERSON_NAME_CRITERION_NAME = "name";
    public static final String PROFESSION_CRITERION_NAME = "professions";

    public static final String RATING_FLOOR_CRITERIA_SUFFIX = "floor";
    public static final String RATING_ROOF_CRITERIA_SUFFIX = "roof";

    public static final String ACTOR_PROFESSION_ID = "actor";
    public static final String DIRECTOR_PROFESSION_ID = "director";
    public static final String SCREENWRITER_PROFESSION_ID = "screenwriter";
    public static final String CINEMATOGRAPHER_PROFESSION_ID = "cinematographer";
    public static final String EDITOR_PROFESSION_ID = "editor";
    public static final String PRODUCER_PROFESSION_ID = "producer";
    public static final String MUSICIAN_PROFESSION_ID = "musician";

    public static final float MAX_AVERAGE_RATING_VALUE = 5.f;
    public static final float MIN_AVERAGE_RATING_VALUE = 1.f;

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 12;
    public static final int DEFAULT_NUMBER_OF_PAGES = 5;

    public static final String FILMS_IMAGES_PATH = "resources/image/film/";
    public static final String PEOPLE_IMAGES_PATH = "resources/image/person/";

}
