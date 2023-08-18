package com.java.dao;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.CustomerCredentials;

public interface CustomerCredentialsDAO {
	boolean addCredentials(CustomerCredentials customerCredentials) throws GenericException;
	boolean updateCredentials(String customerId, String oldPassword, String newPassword) throws GenericException;
	boolean deleteCredentials(String customerId);
	CustomerCredentials getCredentialsById(String customerId) throws GenericException;
	ArrayList<CustomerCredentials> getAllCredentials() throws GenericException;
}
