package com.java.responsedto;

public class CustomerLoginResDTO {
	private String customerId;

	public CustomerLoginResDTO(String customerId) {
		super();
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
