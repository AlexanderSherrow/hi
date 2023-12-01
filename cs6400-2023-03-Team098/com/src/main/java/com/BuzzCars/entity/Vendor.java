package com.BuzzCars.entity;

public class Vendor {
	String name;
	String state;
	String street;
	String city;
	String postal;
	String phone;

	public Vendor() {
		// TODO Auto-generated constructor stub
	}

	public Vendor(String name, String state, String street, String city, String postal, String phone) {
		this.name = name;
		this.state = state;
		this.street = street;
		this.city = city;
		this.postal = postal;
		this.phone = phone;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
