package org.student.filmApp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_")
public class Comment implements Comparable<Comment>, Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentGen")
    @SequenceGenerator(name = "commentGen", sequenceName = "COMMENT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "date_of_publish")
    private LocalDateTime dateOfPublish;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Comment() {}

    public Comment(String content, Film film, User user) {
        this.content = content;
        this.film = film;
        this.user = user;
    }

    public Comment(String content, Person person, User user) {
        this.content = content;
        this.person = person;
        this.user = user;
    }

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int compareTo(Comment c) {
        return c.getDateOfPublish().compareTo(dateOfPublish);
    }
}