package com.java.buisnesslayer;

import java.util.ArrayList;

import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;

public interface ClerkService {
	boolean createCustomer(CreateCustDTO createCustDTO);
	ArrayList<Customer> getAllCustomers();
	ArrayList<LoanApplication> getAllApplications();
}
