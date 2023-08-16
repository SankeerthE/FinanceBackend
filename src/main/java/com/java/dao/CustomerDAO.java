package com.java.dao;

import java.util.ArrayList;

import com.java.entities.Customer;

public interface CustomerDAO {
	 boolean createCustomer(Customer customer);
	 boolean updateCustomer(String CustomerId);
	 boolean deleteCustomer(String CustomerId);
	 ArrayList<Customer> getAllCustomers();
	 Customer getCustomerById(String CustomerId);
}
