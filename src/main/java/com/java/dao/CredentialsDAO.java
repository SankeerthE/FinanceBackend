package com.java.dao;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.Credentials;

public interface CredentialsDAO {
	boolean addCredentials(Credentials credentials) throws GenericException;
	boolean updateCredentials(String username, String oldPassword, String newPassword) throws GenericException;
	boolean deleteCredentials(String username);
	Credentials getCredentialsByName(String username) throws GenericException;
	ArrayList<Credentials> getAllCredentials() throws GenericException;
}
