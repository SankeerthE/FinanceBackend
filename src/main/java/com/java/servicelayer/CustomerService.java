package com.java.servicelayer;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.Customer;
import com.java.requestdto.CreateCustDTO;
import com.java.responsedto.ProfileDTO;

public interface CustomerService {
	ProfileDTO getMyProfile(String customerId) throws GenericException;

	boolean createCustomer(CreateCustDTO createCustDTO) throws GenericException;

	ArrayList<Customer> getAllCustomers() throws GenericException;

}
