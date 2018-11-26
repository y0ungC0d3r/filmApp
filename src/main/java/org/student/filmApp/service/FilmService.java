package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.student.filmApp.entity.*;
import org.student.filmApp.repository.FilmRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
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

    @Transactional
    public Set<Film> findFilmBySearchTerms(MultiValueMap<String, String> criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);
        Root<Film> root = cq.from(Film.class);

        List<Predicate> predicates = new ArrayList<>();

        if(!CollectionUtils.isEmpty(criteria.get(TITLE_CRITERION_NAME))) {
            Predicate polishTitlePredicate = cb.like(root.get(Film_.polishTitle),
                    "%" + criteria.get(TITLE_CRITERION_NAME).get(0) + "%");
            Predicate originalTitlePredicate = cb.like(root.get(Film_.originalTitle),
                    "%" + criteria.get(TITLE_CRITERION_NAME).get(0) + "%");

            Predicate titlePredicate = cb.or(polishTitlePredicate, originalTitlePredicate);
            predicates.add(titlePredicate);
        }

        if(!CollectionUtils.isEmpty(criteria.get(YEARS_CRITERION_NAME))) {
            Predicate[] datePredicates = criteria.get(YEARS_CRITERION_NAME)
                    .stream()
                    .map(Integer::valueOf)
                    .map(y ->
                            cb.between(root.get(Film_.polishReleaseDate), getFirstDateOfYear(y), getLastDateOfYear(y)))
                    .toArray(Predicate[]::new);

            Predicate completeDatePredicate = cb.or(datePredicates);
            predicates.add(completeDatePredicate);
        }

        if(!CollectionUtils.isEmpty(criteria.get(GENRE_CRITERION_NAME))) {
            SetJoin<Film, Genre> genreNode = root.join(Film_.genres);

            List<Long> wantedGenres = criteria.get(GENRE_CRITERION_NAME)
                    .stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            Predicate genresPredicate = genreNode.get(Genre_.id).in(wantedGenres);
            predicates.add(genresPredicate);
        }

        if(!CollectionUtils.isEmpty(criteria.get(COUNTRY_CRITERION_NAME))) {
            SetJoin<Film, Country> countryNode = root.join(Film_.countries);
            Predicate countriesPredicate = countryNode.get(Country_.id).in(criteria.get(COUNTRY_CRITERION_NAME));
            predicates.add(countriesPredicate);
        }

        if(!CollectionUtils.isEmpty(predicates)) {
            Predicate totalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
            cq.where(totalPredicate);
        }

        TypedQuery<Film> q = entityManager.createQuery(cq);
        System.out.println("CHUUUUJ ");
        q.getResultList().forEach(film -> System.out.println(film.getId()));
        return new HashSet<>(q.getResultList());
    }

    private LocalDate getFirstDateOfYear(int year) {
        return LocalDate.of(year, Month.JANUARY, 1);
    }

    private LocalDate getLastDateOfYear(int year) {
        return LocalDate.of(year, Month.DECEMBER, 31);
    }

}
