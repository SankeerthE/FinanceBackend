package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.dao.AccountDAO;
import com.java.entities.Account;
import com.java.jdbcconn.JdbcApp;

public class AccountDAOImpl implements AccountDAO {
	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean createAccount(Account account) {
		try {
			ps = connection.prepareStatement(
					"insert into account(account_number,cust_id,balance,timestamp) values(?,?,?,CURRENT_TIMESTAMP)");
			ps.setString(1, account.getAccountNumber());
			ps.setString(2, account.getCustomerId());
			ps.setDouble(3, account.getBalance());
			int res = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean updateAccount(String accountNumber) {

		try {
			ps = connection.prepareStatement("update account set balance=? where account_number=?");
			ps.setString(1, accountNumber);
			int res = ps.executeUpdate();
			if (res == 0) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
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
	public Account getAccountById(String accountNumber) {
		Account account = null;
		try {
			ps = connection.prepareStatement(
					"select account_number,cust_id,balance,timestamp from account where account_number=?");
			ps.setString(1,accountNumber);
			ResultSet res = ps.executeQuery();
			if (res.getFetchSize() == 0) {
				return null;
			}
			while (res.next()) {
				account = new Account(res.getString(1), res.getString(2), res.getDouble(3), res.getTimestamp(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account;
	}

}