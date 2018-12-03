package org.student.filmApp.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "film_rating")
@AssociationOverrides({
        @AssociationOverride(name = "filmRatingId.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "filmRatingId.film",
                joinColumns = @JoinColumn(name = "film_id")) })
public class FilmRating {

    @EmbeddedId
    private FilmRatingId filmaRatingId = new FilmRatingId();

    @Column(name = "rating")
    private Short rating;

    @Column(name = "date_of_rating")
    private Date dateOfRating;

    private User user;

    private Film film;

    public FilmRatingId getFilmaRatingId() {
        return filmaRatingId;
    }

    public void setFilmaRatingId(FilmRatingId filmaRatingId) {
        this.filmaRatingId = filmaRatingId;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public Date getDateOfRating() {
        return dateOfRating;
    }

    public void setDateOfRating(Date dateOfRating) {
        this.dateOfRating = dateOfRating;
    }

    public User getUser() {
        return user;
    }

    @Transient
    public void setUser(User user) {
        this.user = user;
    }

    public Film getFilm() {
        return film;
    }

    @Transient
    public void setFilm(Film film) {
        this.film = film;
    }
}
