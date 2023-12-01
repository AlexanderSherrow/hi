package com.BuzzCars.entity;

public class Owner {
	String username;

	private User user;

	public Owner() {
	}

	public Owner(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
