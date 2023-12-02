package com.BuzzCars.entity;

public class Manager {
	String username;
	private User user;

	public Manager() {
	}

	public Manager(String username) {
		this.username = username;
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
