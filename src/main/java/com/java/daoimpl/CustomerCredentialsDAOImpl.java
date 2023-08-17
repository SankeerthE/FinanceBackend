package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.dao.CustomerCredentialsDAO;
import com.java.entities.CustomerCredentials;
import com.java.jdbcconn.JdbcApp;

public class CustomerCredentialsDAOImpl implements CustomerCredentialsDAO {

	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean addCredentials(CustomerCredentials customerCredentials) {

		try {
			ps = connection
					.prepareStatement("insert into customer_credentials(cust_id,username,password) values(?,?,?)");
			ps.setString(1, customerCredentials.getCustomerId());
			ps.setString(2, customerCredentials.getUsername());
			ps.setString(3, customerCredentials.getPassword());
			int res = ps.executeUpdate();
			if (res == 0) {
				return false;
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean updateCredentials(String customerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCredentials(String customerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CustomerCredentials getCredentialsById(String customerId) {
		CustomerCredentials customerCredentials = null;
		try {
			ps = connection
					.prepareStatement("select cust_id,username,password from customer_credentials where cust_id=?");
			ps.setString(1, customerId);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				return null;
			}
			while (res.next()) {
				customerCredentials = new CustomerCredentials(res.getString(1), res.getString(2), res.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customerCredentials;
	}

}