package com.java.buisnesslayer;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;
import com.java.requestdto.CreateLoanDTO;

public interface ClerkService {
	boolean createCustomer(CreateCustDTO createCustDTO) throws GenericException;
	ArrayList<Customer> getAllCustomers() throws GenericException;
	ArrayList<LoanApplication> getAllApplications() throws GenericException;
	boolean createLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) throws GenericException;
}
