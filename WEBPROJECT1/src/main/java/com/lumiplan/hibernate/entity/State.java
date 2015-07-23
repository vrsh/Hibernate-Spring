package com.lumiplan.hibernate.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table (name="state")
public class State {
	
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="State_id")
	private int state_id;
	
	@Column (name="State_name")
	private String state_name;
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name="Country_id")
	private Country country_id;
	
	public State(){}
	
	public int getState_id() {
		return state_id;
	}
	public void setState_id(int state_id) {
		this.state_id = state_id;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public Country getCountry() {
		return country_id;
	}

	public void setCountry(Country country) {
		this.country_id = country;
	}
}

