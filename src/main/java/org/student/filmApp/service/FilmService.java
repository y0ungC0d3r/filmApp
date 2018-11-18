package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.student.filmApp.entity.Film;
import org.student.filmApp.entity.Film_;
import org.student.filmApp.entity.Genre;
import org.student.filmApp.entity.Genre_;
import org.student.filmApp.repository.FilmRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    EntityManager entityManager;

    private static final String RATING_CRITERION_NAME = "rating";
    private static final String GENRE_CRITERION_NAME = "genre";
    private static final String COUNTRY_CRITERION_NAME = "country";
    private static final String TITLE_CRITERION_NAME = "title";
    private static final String YEARS_CRITERION_NAME = "years";

    Film findById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    public Set<Film> findFilm(MultiValueMap<String, String> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);

        List<Long> wantedGenres = criteria.get(GENRE_CRITERION_NAME)
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());

        Root<Film> root = cq.from(Film.class);
        SetJoin<Film, Genre> genreNode = root.join(Film_.genres);
        Predicate genresPredicate = genreNode.get(Genre_.id).in(wantedGenres);
        cq.where(genresPredicate);


        //cq.select(root).where(genresPredicate);

        TypedQuery<Film> q = entityManager.createQuery(cq);
        System.out.println("QQQQQ " + q.getSingleResult());
        return null;
    }

    private <T> List<T> getValuesByCriterion(List<String> x, Class<T> clazz) {
        return null;
    }

}
