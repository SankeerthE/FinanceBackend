package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.dao.CustomerCredentialsDAO;
import com.java.entities.CustomerCredentials;
import com.java.jdbcconn.JdbcApp;

public class CustomerCredentialsDAOImpl implements CustomerCredentialsDAO {

	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean addCredentials(CustomerCredentials customerCredentials) throws GenericException {
		boolean status = true;
		try {
			ps = connection
					.prepareStatement("insert into customer_credentials(cust_id,username,password) values(?,?,?)");
			ps.setString(1, customerCredentials.getCustomerId());
			ps.setString(2, customerCredentials.getUsername());
			ps.setString(3, customerCredentials.getPassword());
			int res = ps.executeUpdate();
			if (res == 0) {
				status = false;
				throw new GenericException("failed to add credentials");

			}
		} catch (SQLException e) {
			status = false;
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public boolean updateCredentials(String customerId, String oldPassword, String newPassword)
			throws GenericException {
		boolean status = true;
		try {
			ps = connection
					.prepareStatement("update customer_credentials set password=? where cust_id=? and password=?");
			ps.setString(1, newPassword);
			ps.setString(2, customerId);
			ps.setString(3, oldPassword);
			int res = ps.executeUpdate();
			System.out.println(res);
			if (res == 0) {
				status = false;
				throw new GenericException("incorrect password");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public boolean deleteCredentials(String customerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CustomerCredentials getCredentialsById(String customerId) throws GenericException {
		CustomerCredentials customerCredentials = null;
		try {
			ps = connection
					.prepareStatement("select cust_id,username,password from customer_credentials where cust_id=?");
			ps.setString(1, customerId);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				throw new GenericException("there are no credentials for " + customerId);
			}
			while (res.next()) {
				customerCredentials = new CustomerCredentials(res.getString(1), res.getString(2), res.getString(3));
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return customerCredentials;
	}

	@Override
	public ArrayList<CustomerCredentials> getAllCredentials() throws GenericException {
		ArrayList<CustomerCredentials> customerCredentials = new ArrayList<CustomerCredentials>();
		try {
			ps = connection.prepareStatement("select cust_id,username,password from customer_credentials");
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				customerCredentials.add(new CustomerCredentials(res.getString(1), res.getString(2), res.getString(3)));
			}

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return customerCredentials;
	}

}
