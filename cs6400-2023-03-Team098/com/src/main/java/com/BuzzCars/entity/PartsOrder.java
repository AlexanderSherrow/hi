package com.BuzzCars.entity;

public class PartsOrder {
	String vin;
	String purchaseOrderNumber;
	String vendorName;
	float totalPartsCost;
	String street;
	String state;
	String city;
	String postal;
	String partNumber;
	String description;
	String currentStatus;

	public PartsOrder() {
		// TODO Auto-generated constructor stub
	}

	public PartsOrder(String vin, String purchaseOrderNumber, String vendorName, float totalPartsCost, String street,
			String state, String city, String postal, String partNumber, String description, String currentStatus) {
		super();
		this.vin = vin;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.vendorName = vendorName;
		this.totalPartsCost = totalPartsCost;
		this.street = street;
		this.state = state;
		this.city = city;
		this.postal = postal;
		this.partNumber = partNumber;
		this.description = description;
		this.currentStatus = currentStatus;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public float getTotalPartsCost() {
		return totalPartsCost;
	}

	public void setTotalPartsCost(float totalPartsCost) {
		this.totalPartsCost = totalPartsCost;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

}
