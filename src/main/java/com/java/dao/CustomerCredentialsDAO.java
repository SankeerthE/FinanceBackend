package com.java.dao;

import com.java.entities.CustomerCredentials;

public interface CustomerCredentialsDAO {
	boolean addCredentials(CustomerCredentials customerCredentials);
	boolean updateCredentials(String customerId);
	boolean deleteCredentials(String customerId);
	CustomerCredentials getCredentialsById(String customerId);
}
