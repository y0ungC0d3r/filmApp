package org.student.filmApp.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "person_rating")
@AssociationOverrides({
        @AssociationOverride(name = "personRatingId.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "personRatingId.film",
                joinColumns = @JoinColumn(name = "person_id")) })
public class PersonRating {

    @EmbeddedId
    private PersonRatingId personRatingId = new PersonRatingId();

    @Column(name = "rating")
    private Short rating;

    @Column(name = "date_of_rate")
    private LocalDate dateOfRating;

    public PersonRatingId getPersonRatingId() {
        return personRatingId;
    }

    public void setPersonRatingId(PersonRatingId personRatingId) {
        this.personRatingId = personRatingId;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public LocalDate getDateOfRating() {
        return dateOfRating;
    }

    public void setDateOfRating(LocalDate dateOfRating) {
        this.dateOfRating = dateOfRating;
    }

    public Person getPerson() {
        return personRatingId.getPerson();
    }

    @Transient
    public void setPerson(Person person) {
        this.personRatingId.setPerson(person);
    }

    public User getUser() {
        return personRatingId.getUser();
    }

    @Transient
    public void setUser(User user) {
        this.personRatingId.setUser(user);
    }
}
