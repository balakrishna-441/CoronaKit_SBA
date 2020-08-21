package com.iiht.evaluation.coronokit.model;

public class UserDetails {
	
	private String name;
	private String emailId;
	private String contactNumber;
	private String deliveryAddress;	
	
	public UserDetails(String name, String emailId, String contactNumber, String deliveryAddress) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.deliveryAddress = deliveryAddress;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

}
