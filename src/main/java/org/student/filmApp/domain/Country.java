package org.student.filmApp.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country implements Identifiable<Long> {

	@Id
	@Column(name = "country_id")
	private Long id;
	
	@Column(name = "country_name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "film_country",
			joinColumns = { @JoinColumn(name = "country_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> films;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Film> getFilms() {
		return films;
	}

	public void setFilms(Set<Film> films) {
		this.films = films;
	}
}
