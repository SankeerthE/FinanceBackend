package com.java.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.Account;

public interface AccountDAO {
	boolean createAccount(Account account) throws SQLException;
	boolean updateAccount(String accountNumber) throws SQLException;
	boolean deleteAccount(String accountNumber);
	ArrayList<Account> getAllAccounts();
	Account getAccountById(String customerId) throws SQLException;
}
