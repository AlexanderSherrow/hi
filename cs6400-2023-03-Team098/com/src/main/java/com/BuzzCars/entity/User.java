package com.BuzzCars.entity;

public class User {

	private String userName;
	private String password;
	private String firstName;
	private String LastName;

	public User() {

	}

	public User(String userName, String password, String firstName, String lastName) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.LastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

}
