package com.BuzzCars.entity;

public class Part {
	String partNumber;
	String purchaseOrderNumber;
	int quantity;
	float cost;
	String description;
	String currentStatus;

	public Part() {
		// TODO Auto-generated constructor stub
	}

	public Part(String partNumber, String purchaseOrderNumber, int quantity, float cost, String description,
			String currentStatus) {
		super();
		this.partNumber = partNumber;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.quantity = quantity;
		this.cost = cost;
		this.description = description;
		this.currentStatus = currentStatus;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
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

}
