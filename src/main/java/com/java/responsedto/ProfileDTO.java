package com.java.responsedto;

public class ProfileDTO {
	private String customerName;
	private String customerGender;
	private String customerEmail;
	private String customerMobile;
	private String accountNumber;
	private double balance;

	public ProfileDTO(String customerName, String customerGender, String customerEmail, String customerMobile,
			String accountNumber, double balance) {
		super();
		this.customerName = customerName;
		this.customerGender = customerGender;
		this.customerEmail = customerEmail;
		this.customerMobile = customerMobile;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
