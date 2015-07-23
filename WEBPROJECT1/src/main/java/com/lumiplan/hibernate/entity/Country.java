package com.lumiplan.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "country")
public class Country {
	
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name = "Country_id")
	private int country_id;
	
	@Column (name="Country_name")
	private String country_name;
	
	public Country(){}
	
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int countryid) {
		this.country_id = countryid;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String countryName) {
		this.country_name = countryName;
	}
	
}
