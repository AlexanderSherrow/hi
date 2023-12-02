package com.BuzzCars.entity;

public class Customer {

	private String businessTaxId;

	private String driversLicenseNumber;

	private String street;

	private String city;

	private String state;

	private String postalCode;

	private String phoneNumber;

	private String email;

	private Business business;

	private Individual individual;

	public Customer() {

	}

	public Customer(String businessTaxId, String driversLicenseNumber, String street, String city, String state,
			String postalCode, String phoneNumber, String email) {
		super();
		this.businessTaxId = businessTaxId;
		this.driversLicenseNumber = driversLicenseNumber;
		this.street = street;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getBusinessTaxId() {
		return businessTaxId;
	}

	public void setBusinessTaxId(String businessTaxId) {
		this.businessTaxId = businessTaxId;
	}

	public String getDriversLicenseNumber() {
		return driversLicenseNumber;
	}

	public void setDriversLicenseNumber(String driversLicenseNumber) {
		this.driversLicenseNumber = driversLicenseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
