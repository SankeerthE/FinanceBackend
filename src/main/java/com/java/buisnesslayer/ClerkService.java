package com.java.buisnesslayer;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;
import com.java.requestdto.CreateLoanDTO;

public interface ClerkService {
	boolean createCustomer(CreateCustDTO createCustDTO) throws SQLException;
	ArrayList<Customer> getAllCustomers() throws SQLException;
	ArrayList<LoanApplication> getAllApplications() throws SQLException;
	boolean createLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) throws SQLException;
}
