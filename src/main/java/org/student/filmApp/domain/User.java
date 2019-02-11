package org.student.filmApp.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_")
public class User implements Identifiable<Long> {

    public User() {}

    public User(Long id) {
        this.id = id;
    }

    private Long id;

    private String username;

    private String password;

    private String passwordConfirm;

    private Set<Role> roles;

    private Set<FilmRating> filmRatings;

    private Set<PersonRating> personRatings;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filmRatingId.user")
    public Set<FilmRating> getFilmRatings() {
        return filmRatings;
    }

    public void setFilmRatings(Set<FilmRating> filmRatings) {
        this.filmRatings = filmRatings;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personRatingId.user")
    public Set<PersonRating> getPersonRatings() {
        return personRatings;
    }

    public void setPersonRatings(Set<PersonRating> personRatings) {
        this.personRatings = personRatings;
    }
}