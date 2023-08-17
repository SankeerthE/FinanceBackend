package com.java.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.CustomerCredentials;

public interface CustomerCredentialsDAO {
	boolean addCredentials(CustomerCredentials customerCredentials) throws SQLException;
	boolean updateCredentials(String customerId);
	boolean deleteCredentials(String customerId);
	CustomerCredentials getCredentialsById(String customerId);
	ArrayList<CustomerCredentials> getAllCredentials() throws SQLException;
}
