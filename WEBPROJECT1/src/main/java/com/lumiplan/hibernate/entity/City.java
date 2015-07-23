package com.lumiplan.hibernate.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="city")
public class City {
	
	@Id @GeneratedValue
	@Column (name="City_id", unique=true)
	private int city_id;
	
	@Column (name="City_name", unique=true)
	private String city_name;
	
	@ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name="State_id")
	private State state_id;
	
	public City(){}
	
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int cityid) {
		this.city_id = cityid;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String cityName) {
		this.city_name = cityName;
	}
	
	public State getState(){
		return state_id;
	}
	public void setState(State stateid) {
		this.state_id = stateid;
	}
}
