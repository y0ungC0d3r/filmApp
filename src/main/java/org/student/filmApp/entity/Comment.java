package org.student.filmApp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "column")
    private Long id;

    @Column(name = "date_of_publish")
    private LocalDateTime dateOfPublish;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public LocalDateTime getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(LocalDateTime dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
