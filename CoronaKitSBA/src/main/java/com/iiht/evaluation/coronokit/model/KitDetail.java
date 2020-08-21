package com.iiht.evaluation.coronokit.model;

public class KitDetail {

	private int id;
	private int coronaKitId;
	private ProductMaster productData;
	private int quantity;
	private double amount;
		
	public KitDetail(int id, int coronaKitId, ProductMaster product, int quantity, double amount) {
		this.id = id;
		this.coronaKitId = coronaKitId;
		this.productData = product;
		this.quantity = quantity;
		this.amount = amount;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCoronaKitId() {
		return coronaKitId;
	}
	public void setCoronaKitId(int coronaKitId) {
		this.coronaKitId = coronaKitId;
	}
	public ProductMaster getProduct() {
		return productData;
	}
	public void setProduct(ProductMaster productData) {
		this.productData = productData;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
