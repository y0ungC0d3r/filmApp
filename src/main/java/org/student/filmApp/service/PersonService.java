package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.student.filmApp.entity.*;
import org.student.filmApp.repository.PersonRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.student.filmApp.Consts.*;
import static org.student.filmApp.Consts.MAX_AVERAGE_RATING_VALUE;
import static org.student.filmApp.Consts.MIN_AVERAGE_RATING_VALUE;
import static org.student.filmApp.service.FilmService.SORT_BY_VALUE_PATTERN;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManager entityManager;

    @Transactional
    public Person findByIdWithFetch(Long personId) {
        return personRepository.findByIdWithFetch(personId);
    }

    @Transactional
    public Long countPeopleBySearchTerms(MultiValueMap<String, String> criteria) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Person> countRoot = countCriteria.from(Person.class);
        Long numberOfRows = entityManager.createQuery(
                countCriteria
                        .select(builder.count(countRoot))
                        .where(getTotalPredicate(criteria, countRoot, builder, countCriteria))
        ).getSingleResult();

        return numberOfRows;
    }

    @Transactional
    public List<Person> findPeopleBySearchTerms(MultiValueMap<String, String> criteria, int currPageNumber, int lastPageNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Person> pageCriteria = builder.createQuery(Person.class);
        Root<Person> pageRoot = pageCriteria.from(Person.class);

        if(currPageNumber > lastPageNumber) {
            currPageNumber = lastPageNumber;
        }

        List<Person> people = entityManager.createQuery(
                pageCriteria
                        .select(pageRoot)
                        .where(getTotalPredicate(criteria, pageRoot, builder, pageCriteria))
                        .orderBy(getOrder(criteria.get(SORT_BY_CRITERION_NAME), pageRoot, builder))
        ).setFirstResult((currPageNumber - 1) * DEFAULT_PAGE_SIZE)
                .setMaxResults(DEFAULT_PAGE_SIZE)
                .getResultList();

        return people;
    }

    private Predicate getTotalPredicate(MultiValueMap<String, String> criteriaMap,
                                        Root<Person> root, CriteriaBuilder builder, CriteriaQuery<?> pageCriteria) {
        List<Predicate> predicates = new ArrayList<>();

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

            Predicate floorRatingPredicate = builder.greaterThanOrEqualTo(root.get(Person_.averageRating), floor.orElse(MIN_AVERAGE_RATING_VALUE));
            predicates.add(floorRatingPredicate);

            Predicate roofRatingPredicate = builder.lessThanOrEqualTo(root.get(Person_.averageRating), roof.orElse(MAX_AVERAGE_RATING_VALUE));
            predicates.add(roofRatingPredicate);
        }

        if(!CollectionUtils.isEmpty(criteriaMap.get(PERSON_NAME_CRITERION_NAME)) &&
                !criteriaMap.get(PERSON_NAME_CRITERION_NAME).get(0).isEmpty()) {
            Predicate stageNamePredicate = builder.like(root.get(Person_.stageName),
                    "%" + criteriaMap.get(PERSON_NAME_CRITERION_NAME).get(0) + "%");
            Predicate fullNamePredicate = builder.like(root.get(Person_.fullName),
                    "%" + criteriaMap.get(PERSON_NAME_CRITERION_NAME).get(0) + "%");

            Predicate namePredicate = builder.or(stageNamePredicate, fullNamePredicate);
            predicates.add(namePredicate);
        }

        if(!CollectionUtils.isEmpty(criteriaMap.get(PROFESSION_CRITERION_NAME)) &&
                !criteriaMap.get(PROFESSION_CRITERION_NAME).get(0).isEmpty()) {

            Subquery<Long> sub = pageCriteria.subquery(Long.class);
            Root<Film> personSubRoot = sub.from(Film.class);

            SetJoin<Film, Person> subPeople;
            switch(criteriaMap.get(PROFESSION_CRITERION_NAME).get(0)) {
                case ACTOR_PROFESSION_ID:
                    subPeople = personSubRoot.join(Film_.filmActors);
                    break;
                case DIRECTOR_PROFESSION_ID:
                    subPeople = personSubRoot.join(Film_.filmDirectors);
                    break;
                case SCREENWRITER_PROFESSION_ID:
                    subPeople = personSubRoot.join(Film_.filmScreenwriters);
                    break;
                case CINEMATOGRAPHER_PROFESSION_ID:
                    subPeople = personSubRoot.join(Film_.filmCinematographers);
                    break;
                case EDITOR_PROFESSION_ID:
                    subPeople = personSubRoot.join(Film_.filmEditors);
                    break;
                case PRODUCER_PROFESSION_ID:
                    subPeople = personSubRoot.join(Film_.filmProducers);
                    break;
                case MUSICIAN_PROFESSION_ID:
                    subPeople = personSubRoot.join(Film_.filmMusicians);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            sub.select(builder.count(personSubRoot.get(Film_.id)));
            sub.where(builder.equal(root.get(Person_.id), subPeople.get(Person_.id)));

            Predicate professionPredicate = builder.greaterThan(sub, 0L);
            predicates.add(professionPredicate);
        }

        Predicate totalPredicate = builder.and(predicates.stream().toArray(Predicate[]::new));

        return totalPredicate;
    }

    private Order getOrder(List<String> sortByCriterion, Root<Person> pageRoot, CriteriaBuilder builder) {
        if(!CollectionUtils.isEmpty(sortByCriterion) && sortByCriterion.get(0).matches(SORT_BY_VALUE_PATTERN)) {
            String[] sortCriterium = sortByCriterion.get(0).split("-");
            if(sortCriterium[0].equals("date")) {
                Path<LocalDate> dateOfBirth = pageRoot.get(Person_.dateOfBirth);
                if(sortCriterium[1].equals("descending")) {
                    return builder.desc(dateOfBirth);
                }
                return builder.asc(dateOfBirth);
            } else if(sortCriterium[0].equals("rating")) {
                Path<Float> averageRating = pageRoot.get(Person_.averageRating);
                if(sortCriterium[1].equals("descending")) {
                    return builder.desc(averageRating);
                }
                return builder.asc(averageRating);
            }
        }
        return builder.desc(pageRoot.get(Person_.averageRating));
    }
}
