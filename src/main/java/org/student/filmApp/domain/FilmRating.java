package org.student.filmApp.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "film_rating")
@AssociationOverrides({
        @AssociationOverride(name = "filmRatingId.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "filmRatingId.film",
                joinColumns = @JoinColumn(name = "film_id")) })
public class FilmRating {

    public FilmRating() {}

    public FilmRating(Film film, User user) {
        setFilm(film);
        setUser(user);
    }

    @EmbeddedId
    private FilmRatingId filmRatingId = new FilmRatingId();

    @Column(name = "rating")
    private Short rating;

    @Column(name = "date_of_rate")
    private LocalDate dateOfRating;

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

    public LocalDate getDateOfRating() {
        return dateOfRating;
    }

    public void setDateOfRating(LocalDate dateOfRating) {
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
