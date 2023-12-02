package com.BuzzCars.entity;

public class InventoryClerk {
	String username;

	private User user;

	public InventoryClerk() {
	}

	public InventoryClerk(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
