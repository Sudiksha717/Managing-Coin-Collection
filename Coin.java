package com.priti.casestudy3;

import java.time.LocalDate;
import java.time.Year;

public class Coin {
	
	int id;
	String country;
	String denomination;
	int year_of_minting;
	float current_value;
	LocalDate acquired_date;
	
	public Coin() {
		
	}
	

	public Coin(int id, String country, String denomination, int year_of_minting, float current_value,
			String acquired_date)
	{
		super();
		this.id = id;
		this.country = country;
		this.denomination = denomination;
		this.year_of_minting = year_of_minting;
		this.current_value = current_value;
		this.acquired_date = LocalDate.parse(acquired_date);
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public int getYear_of_minting() {
		return year_of_minting;
	}

	public void setYear_of_minting(int year_of_minting) {
		this.year_of_minting = year_of_minting;
	}

	public float getCurrent_value() {
		return current_value;
	}

	public void setCurrent_value(float current_value) {
		this.current_value = current_value;
	}

	public LocalDate getAcquired_date() {
		return acquired_date;
	}

	public void setAcquired_date(LocalDate acquired_date) {
		this.acquired_date = acquired_date;
	}

	public String toString() {
		return "\n id=" + id + " country=" + country + " denomination=" + denomination + " year_of_minting="
				+ year_of_minting + " current_value=" + current_value + " acquired_date=" + acquired_date + "";
	}
	
	
	

}
