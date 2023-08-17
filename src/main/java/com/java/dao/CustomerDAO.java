package com.java.dao;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.Customer;

public interface CustomerDAO {
	 boolean createCustomer(Customer customer) throws GenericException;
	 boolean updateCustomer(String CustomerId);
	 boolean deleteCustomer(String CustomerId) throws GenericException;
	 ArrayList<Customer> getAllCustomers() throws GenericException;
	 Customer getCustomerById(String CustomerId) throws GenericException;
}
