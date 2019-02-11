package org.student.filmApp.domain;

import java.util.ArrayList;
import java.util.List;

import static org.student.filmApp.Consts.*;

public class Profession implements Identifiable<String> {

    private static List<Profession> allprofessions = new ArrayList<>();

    static {
        allprofessions.add(new Profession(ACTOR_PROFESSION_ID, "aktorstwo"));
        allprofessions.add(new Profession(DIRECTOR_PROFESSION_ID, "reżyseria"));
        allprofessions.add(new Profession(SCREENWRITER_PROFESSION_ID, "scenariusz"));
        allprofessions.add(new Profession(CINEMATOGRAPHER_PROFESSION_ID, "zdjęcia"));
        allprofessions.add(new Profession(EDITOR_PROFESSION_ID, "montaż"));
        allprofessions.add(new Profession(PRODUCER_PROFESSION_ID, "produkcja"));
        allprofessions.add(new Profession(MUSICIAN_PROFESSION_ID, "muzyka"));
    }

    private String id;
    private String name;

    public Profession(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Profession> getAllprofessions() {
        return allprofessions;
    }

    public static void setAllprofessions(List<Profession> allprofessions) {
        Profession.allprofessions = allprofessions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
