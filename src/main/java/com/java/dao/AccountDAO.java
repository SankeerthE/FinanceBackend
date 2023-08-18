package com.java.dao;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.Account;

public interface AccountDAO {
	boolean createAccount(Account account) throws GenericException;
	boolean updateAccount(String accountNumber, Account account) throws GenericException;
	boolean deleteAccount(String accountNumber);
	ArrayList<Account> getAllAccounts();
	Account getAccountById(String customerId) throws GenericException;
}
