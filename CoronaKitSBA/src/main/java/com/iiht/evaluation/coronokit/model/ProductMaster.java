package com.iiht.evaluation.coronokit.model;

public class ProductMaster {

	private int id;
	private String productName;
	private double cost;
	private String productDescription;

	public ProductMaster() {
		// TODO Auto-generated constructor stub
	}

	public ProductMaster(int id, String productName, String productDescription, double cost) {
		this(productName, productDescription, cost);
		this.id = id;	
	}

	public ProductMaster(String productName, String productDescription, double cost) {
		this.productName = productName;
		this.cost = cost;
		this.productDescription = productDescription;
	}
	
	public ProductMaster(int id, String productDescription, double cost) {
		this.id = id;
		this.cost = cost;
		this.productDescription = productDescription;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

}
