package com.java.buisnesslayer;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;

public interface ClerkService {
	boolean createCustomer(CreateCustDTO createCustDTO) throws SQLException;
	ArrayList<Customer> getAllCustomers();
	ArrayList<LoanApplication> getAllApplications();
}
