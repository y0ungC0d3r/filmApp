package org.student.filmApp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {

	@Id
	@Column(name = "country_code_id")
	private String countryCodeID;
	
	@Column(name = "country_name")
	private String countryName;

	public String getCountryCodeID() {
		return countryCodeID;
	}

	public void setCountryCodeID(String countryCodeID) {
		this.countryCodeID = countryCodeID;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
}
