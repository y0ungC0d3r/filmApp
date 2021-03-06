package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.student.filmApp.domain.*;
import org.student.filmApp.repository.FilmRepository;
import static org.student.filmApp.Consts.*;
import static org.student.filmApp.utils.DateUtils.getFirstDayOfYear;
import static org.student.filmApp.utils.DateUtils.getLastDayOfYear;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    EntityManager entityManager;

    public static final String SORT_BY_VALUE_PATTERN = "(rating|date)-(ascending|descending)";

    @Transactional
    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    @Transactional
    public Film findByIdWithFetch(Long filmId) {
        return filmRepository.findByIdWithFetch(filmId);
    }

    @Transactional
    public void save(Film film) {
        filmRepository.save(film);
    }

    @Transactional
    public Long countFilmsBySearchTerms(MultiValueMap<String, String> criteria) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Film> countRoot = countCriteria.from(Film.class);
        Long numberOfRows = entityManager.createQuery(
                countCriteria
                        .select(builder.count(countRoot))
                        .where(getTotalPredicate(criteria, countRoot, builder))
        ).getSingleResult();

        return numberOfRows;
    }

    @Transactional
    public List<Film> findFilmsBySearchTerms(MultiValueMap<String, String> criteria,
                                             int currPageNumber, int lastPageNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Film> pageCriteria = builder.createQuery(Film.class);
        Root<Film> pageRoot = pageCriteria.from(Film.class);

        if(currPageNumber > lastPageNumber) {
            currPageNumber = lastPageNumber;
        }

        List<Film> films = entityManager.createQuery(
                pageCriteria
                        .select(pageRoot)
                        .where(getTotalPredicate(criteria, pageRoot, builder))
                        .orderBy(getOrder(criteria.get(SORT_BY_CRITERION_NAME), pageRoot, builder))
        ).setFirstResult((currPageNumber - 1) * DEFAULT_PAGE_SIZE)
                .setMaxResults(DEFAULT_PAGE_SIZE)
                .getResultList();

        return films;
    }


    private Predicate getTotalPredicate(MultiValueMap<String, String> criteriaMap,
                                        Root<Film> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if(!CollectionUtils.isEmpty(criteriaMap.get(FILM_TITLE_CRITERION_NAME)) &&
                !criteriaMap.get(FILM_TITLE_CRITERION_NAME).get(0).isEmpty()) {
            Predicate polishTitlePredicate = builder.like(root.get(Film_.polishTitle),
                    "%" + criteriaMap.get(FILM_TITLE_CRITERION_NAME).get(0) + "%");
            Predicate originalTitlePredicate = builder.like(root.get(Film_.originalTitle),
                    "%" + criteriaMap.get(FILM_TITLE_CRITERION_NAME).get(0) + "%");

            Predicate titlePredicate = builder.or(polishTitlePredicate, originalTitlePredicate);
            predicates.add(titlePredicate);
        }

        if(!CollectionUtils.isEmpty(criteriaMap.get(RATING_CRITERION_NAME))) {
            Optional<Float> floor = criteriaMap.get(RATING_CRITERION_NAME)
                    .stream()
                    .filter(f -> f.startsWith(RATING_FLOOR_CRITERIA_SUFFIX))
                    .map(f -> f.charAt(f.length() - 1))
                    .map(String::valueOf)
                    .map(Float::valueOf)
                    .findFirst();

            Optional<Float> roof = criteriaMap.get(RATING_CRITERION_NAME)
                    .stream()
                    .filter(f -> f.startsWith(RATING_ROOF_CRITERIA_SUFFIX))
                    .map(f -> f.charAt(f.length() - 1))
                    .map(String::valueOf)
                    .map(Float::valueOf)
                    .findFirst();

            Predicate floorRatingPredicate = builder.greaterThanOrEqualTo(root.get(Film_.averageRating), floor.orElse(MIN_AVERAGE_RATING_VALUE));
            predicates.add(floorRatingPredicate);

            Predicate roofRatingPredicate = builder.lessThanOrEqualTo(root.get(Film_.averageRating), roof.orElse(MAX_AVERAGE_RATING_VALUE));
            predicates.add(roofRatingPredicate);
        }

        if(!CollectionUtils.isEmpty(criteriaMap.get(YEARS_CRITERION_NAME))) {
            Predicate[] datePredicates = criteriaMap.get(YEARS_CRITERION_NAME)
                    .stream()
                    .map(Integer::valueOf)
                    .map(y -> builder.between(
                            root.get(Film_.polishReleaseDate), getFirstDayOfYear(y), getLastDayOfYear(y))
                    ).toArray(Predicate[]::new);

            Predicate completeDatePredicate = builder.or(datePredicates);
            predicates.add(completeDatePredicate);
        }

        if(!CollectionUtils.isEmpty(criteriaMap.get(FILM_GENRE_CRITERION_NAME))) {
            SetJoin<Film, Genre> genreNode = root.join(Film_.genres);

            List<Long> requestedGenres = criteriaMap.get(FILM_GENRE_CRITERION_NAME)
                    .stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            Predicate genresPredicate = genreNode.get(Genre_.id).in(requestedGenres);
            predicates.add(genresPredicate);
        }

        if(!CollectionUtils.isEmpty(criteriaMap.get(FILM_COUNTRY_CRITERION_NAME))) {
            SetJoin<Film, Country> countryNode = root.join(Film_.countries);
            Predicate countriesPredicate = countryNode.get(Country_.id).in(criteriaMap.get(FILM_COUNTRY_CRITERION_NAME));
            predicates.add(countriesPredicate);
        }

        Predicate totalPredicate = builder.and(predicates.stream().toArray(Predicate[]::new));

        return totalPredicate;
    }

    private Order getOrder(List<String> sortByCriterion, Root<Film> pageRoot, CriteriaBuilder builder) {
        if(!CollectionUtils.isEmpty(sortByCriterion) &&
                sortByCriterion.get(0).matches(SORT_BY_VALUE_PATTERN)) {
            String[] sortCriterion = sortByCriterion.get(0).split("-");
            if(sortCriterion[0].equals("date")) {
                Path<LocalDate> releaseDate = pageRoot.get(Film_.worldwideReleaseDate);
                if(sortCriterion[1].equals("descending")) {
                    return builder.desc(releaseDate);
                }
                return builder.asc(releaseDate);
            } else if(sortCriterion[0].equals("rating")) {
                Path<Float> averageRating = pageRoot.get(Film_.averageRating);
                if(sortCriterion[1].equals("descending")) {
                    return builder.desc(averageRating);
                }
                return builder.asc(averageRating);
            }
        }
        return builder.desc(pageRoot.get(Film_.averageRating));
    }

}
