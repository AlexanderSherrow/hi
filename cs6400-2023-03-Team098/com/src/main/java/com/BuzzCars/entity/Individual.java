package com.BuzzCars.entity;

public class Individual {

	String driversLicenseNumber;

	String firstName;

	String lastName;

	public Individual() {
		// TODO Auto-generated constructor stub
	}

	public Individual(String driversLicenseNumber, String businessName, String contactTitle, String firstName,
			String lastName) {
		super();
		this.driversLicenseNumber = driversLicenseNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
