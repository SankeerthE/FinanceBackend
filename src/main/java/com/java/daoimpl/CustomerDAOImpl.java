package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.dao.CustomerDAO;
import com.java.entities.Customer;
import com.java.jdbcconn.JdbcApp;

public class CustomerDAOImpl implements CustomerDAO {

	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean createCustomer(Customer customer) {

		try {
			ps = connection.prepareStatement(
					"insert into customer(cust_id,cust_name,cust_gender,cust_email,cust_mobile,timestamp) values(?,?,?,?,?,CURRENT_TIMESTAMP)");
			ps.setString(1, customer.getCustomerId());
			ps.setString(2, customer.getCustomerName());
			ps.setString(3, customer.getCustomerGender());
			ps.setString(4, customer.getCustomerEmail());
			ps.setString(5, customer.getCustomerMobile());
			int res = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean updateCustomer(String CustomerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCustomer(String CustomerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerById(String CustomerId) {
		Customer customer = null;

		try {
			ps = connection.prepareStatement(
					"select cust_id,cust_name,cust_gender,cust_email,cust_mobile,timestamp from customer where cust_id=?");
			ps.setString(1, CustomerId);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				return null;
			}
			while (res.next()) {
				customer = new Customer(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getTimestamp(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customer;
	}

}