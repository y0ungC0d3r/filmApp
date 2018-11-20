package org.student.filmApp.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {

	@Id
	@Column(name = "country_code_id")
	private String codeId;
	
	@Column(name = "country_name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "film_country",
			joinColumns = { @JoinColumn(name = "country_id") },
			inverseJoinColumns = { @JoinColumn(name = "film_id") }
	)
	private Set<Film> films;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
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
