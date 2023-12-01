package com.BuzzCars.entity;

public class Vehicle {
	String modelName;

	int modelYear;

	String description;

	String vin;

	int mileage;

	String manufacturer;

	String chassisType;

	String fuelType;

	float salesPrice;

	String color;

	float costOfParts;

	public Vehicle() {
		// TODO Auto-generated constructor stub
	}

	public Vehicle(String modelName, int modelYear, String description, String vin, int mileage, String manufacturer,
			String chassisType, String fuelType, float salesPrice, String color, float costOfParts) {
		super();
		this.modelName = modelName;
		this.modelYear = modelYear;
		this.description = description;
		this.vin = vin;
		this.mileage = mileage;
		this.manufacturer = manufacturer;
		this.chassisType = chassisType;
		this.fuelType = fuelType;
		this.salesPrice = salesPrice;
		this.color = color;
		this.costOfParts = 0;
	}

	public float getCostOfParts() {
		return costOfParts;
	}

	public void setCostOfParts(float costOfParts) {
		this.costOfParts = costOfParts;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(float salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getModelYear() {
		return modelYear;
	}

	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getChassisType() {
		return chassisType;
	}

	public void setChassisType(String chassisType) {
		this.chassisType = chassisType;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

}
