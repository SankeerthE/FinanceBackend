package com.java.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.Customer;

public interface CustomerDAO {
	 boolean createCustomer(Customer customer) throws SQLException;
	 boolean updateCustomer(String CustomerId);
	 boolean deleteCustomer(String CustomerId);
	 ArrayList<Customer> getAllCustomers();
	 Customer getCustomerById(String CustomerId) throws SQLException;
}
