package com.java.dao;

import java.util.ArrayList;

import com.java.entities.Account;

public interface AccountDAO {
	boolean createAccount(Account account);
	boolean updateAccount(String accountNumber);
	boolean deleteAccount(String accountNumber);
	ArrayList<Account> getAllAccounts();
	Account getAccountById(String accountId);
}
