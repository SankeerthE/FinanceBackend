package com.java.entities;

import java.sql.Timestamp;

public class LoanApplication {
	private String application_number;
	private String cust_id;
	private String loan_id;
	private double amount;
	private int tenure;
	private double emi;
	private String status;
	private Timestamp timestamp;
	
	public LoanApplication(String application_number, String cust_id, String loan_id, double amount, int tenure,
			double emi, String status, Timestamp timestamp) {
		super();
		this.application_number = application_number;
		this.cust_id = cust_id;
		this.loan_id = loan_id;
		this.amount = amount;
		this.tenure = tenure;
		this.emi = emi;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public String getApplication_number() {
		return application_number;
	}
	public void setApplication_number(String application_number) {
		this.application_number = application_number;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	public double getEmi() {
		return emi;
	}
	public void setEmi(double emi) {
		this.emi = emi;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null)
			return false;

		if (this == arg0)
			return true;

		if (!(arg0 instanceof LoanApplication))
			return false;
		LoanApplication other = (LoanApplication) arg0;
		if (this.application_number != other.application_number	)
			return false;

		return true;
	}
	
	
}
