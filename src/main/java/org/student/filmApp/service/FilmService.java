package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.student.filmApp.entity.*;
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

    Film findById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    @Transactional
    public Set<Film> findFilmBySearchTerms(MultiValueMap<String, String> criteria) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Film> countRoot = countCriteria.from(Film.class);
        Long numberOfRows = entityManager.createQuery(
                countCriteria
                        .select(builder.count(countRoot))
                        .where(getTotalPredicate(criteria, countRoot, builder))
        ).getSingleResult();


        int pageNumber = DEFAULT_PAGE_NUMBER;
        if(!CollectionUtils.isEmpty(criteria.get(PAGE_CRITERION_NAME))) {
            pageNumber = Integer.parseInt(criteria.get(PAGE_CRITERION_NAME).get(0));
        }


        CriteriaQuery<Film> pageCriteria = builder.createQuery(Film.class);
        Root<Film> pageRoot = pageCriteria.from(Film.class);

        if(!CollectionUtils.isEmpty(criteria.get(SORT_BY_CRITERION_NAME))) {
            String[] sortCriterium = criteria.get(SORT_BY_CRITERION_NAME).get(0).split("-");
            if(sortCriterium[0] == "date") {
                Path<LocalDate> date = pageRoot.get(Film_.polishReleaseDate);
            } else {
                //Path<>
            }
        }

        List<Film> films = entityManager.createQuery(
                pageCriteria
                        .select(pageRoot)
                        .where(getTotalPredicate(criteria, pageRoot, builder))
                        .orderBy(builder.desc(pageRoot.get(Film_.polishReleaseDate)))
        ).setFirstResult((pageNumber - 1) * DEFAULT_PAGE_SIZE)
                .setMaxResults(DEFAULT_PAGE_SIZE)
                .getResultList();

        return new HashSet<>(films);
    }


    private Predicate getTotalPredicate(MultiValueMap<String, String> criteriaMap, Root<Film> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if(!CollectionUtils.isEmpty(criteriaMap.get(TITLE_CRITERION_NAME))) {
            Predicate polishTitlePredicate = builder.like(root.get(Film_.polishTitle),
                    "%" + criteriaMap.get(TITLE_CRITERION_NAME).get(0) + "%");
            Predicate originalTitlePredicate = builder.like(root.get(Film_.originalTitle),
                    "%" + criteriaMap.get(TITLE_CRITERION_NAME).get(0) + "%");

            Predicate titlePredicate = builder.or(polishTitlePredicate, originalTitlePredicate);
            predicates.add(titlePredicate);
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

        if(!CollectionUtils.isEmpty(criteriaMap.get(GENRE_CRITERION_NAME))) {
            SetJoin<Film, Genre> genreNode = root.join(Film_.genres);

            List<Long> requestedGenres = criteriaMap.get(GENRE_CRITERION_NAME)
                    .stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            Predicate genresPredicate = genreNode.get(Genre_.id).in(requestedGenres);
            predicates.add(genresPredicate);
        }

        if(!CollectionUtils.isEmpty(criteriaMap.get(COUNTRY_CRITERION_NAME))) {
            SetJoin<Film, Country> countryNode = root.join(Film_.countries);
            Predicate countriesPredicate = countryNode.get(Country_.id).in(criteriaMap.get(COUNTRY_CRITERION_NAME));
            predicates.add(countriesPredicate);
        }

        Predicate totalPredicate = builder.and(predicates.stream().toArray(Predicate[]::new));

        return totalPredicate;
    }

}
