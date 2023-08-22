package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.dao.AccountDAO;
import com.java.entities.Account;
import com.java.jdbcconn.JdbcApp;

public class AccountDAOImpl implements AccountDAO {
	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean createAccount(Account account) throws GenericException {
		boolean status=true;
		try {
			ps = connection.prepareStatement(
					"insert into account(account_number,cust_id,balance,timestamp) values(?,?,?,CURRENT_TIMESTAMP)");
			ps.setString(1, account.getAccountNumber());
			ps.setString(2, account.getCustomerId());
			ps.setDouble(3, account.getBalance());
			int res = ps.executeUpdate();
			if(res==0) {
				status=false;
				throw new GenericException("failed to create the account");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public boolean updateAccount(String accountNumber, Account account) throws GenericException {
		boolean status=true;
		try {
			ps = connection.prepareStatement("update account set balance=? where account_number=?");
			ps.setDouble(1, account.getBalance());
			ps.setString(2, accountNumber);
			int res = ps.executeUpdate();
			if (res == 0) {
				status=false;
				throw new GenericException("failed to update account balance");
			}

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public boolean deleteAccount(String accountNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountById(String customerId) throws GenericException {
		Account account = null;
		System.out.println(customerId);
		try {
			ps = connection
					.prepareStatement("select account_number,cust_id,balance,timestamp from account where cust_id=?");
			ps.setString(1, customerId);
			ResultSet res = ps.executeQuery();
			if (res.getFetchSize() == 0) {
				throw new GenericException("there is no account present for "+customerId);
			}
			System.out.println(res.getFetchSize());
			while (res.next()) {
				account = new Account(res.getString(1), res.getString(2), res.getDouble(3), res.getTimestamp(4));
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return account;
	}

}
