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
    private FilmRatingId filmRatingId = new FilmRatingId();

    @Column(name = "rating")
    private Short rating;

    @Column(name = "date_of_rating")
    private Date dateOfRating;

    public FilmRatingId getFilmRatingId() {
        return filmRatingId;
    }

    public void setFilmRatingId(FilmRatingId filmRatingId) {
        this.filmRatingId = filmRatingId;
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
        return filmRatingId.getUser();
    }

    @Transient
    public void setUser(User user) {
        filmRatingId.setUser(user);
    }

    public Film getFilm() {
        return filmRatingId.getFilm();
    }

    @Transient
    public void setFilm(Film film) {
        filmRatingId.setFilm(film);
    }
}
