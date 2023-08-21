package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.dao.CustomerDAO;
import com.java.entities.Customer;
import com.java.jdbcconn.JdbcApp;

public class CustomerDAOImpl implements CustomerDAO {

	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean createCustomer(Customer customer) throws GenericException {
		boolean status = false;
		try {
			ps = connection.prepareStatement(
					"insert into customer(cust_id,cust_name,cust_gender,cust_email,cust_mobile,timestamp) values(?,?,?,?,?,CURRENT_TIMESTAMP)");
			ps.setString(1, customer.getCustomerId());
			ps.setString(2, customer.getCustomerName());
			ps.setString(3, customer.getCustomerGender());
			ps.setString(4, customer.getCustomerEmail());
			ps.setString(5, customer.getCustomerMobile());
			int res = ps.executeUpdate();
			if (res == 0) {
				throw new GenericException("failed to create customer");
			}
			status = true;

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public boolean updateCustomer(String CustomerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCustomer(String CustomerId) throws GenericException {
		boolean status = true;

		try {
			ps = connection.prepareStatement("delete from customer where cust_id=?");
			ps.setString(1, CustomerId);
			int res = ps.executeUpdate();
			if (res == 0) {
				status = false;
				throw new GenericException("failed to delete customerId");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws GenericException {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			ps = connection.prepareStatement(
					"select cust_id,cust_name,cust_gender,cust_email,cust_mobile,timestamp from customer");
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				customers.add(new Customer(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getTimestamp(6)));
			}

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return customers;
	}

	@Override
	public Customer getCustomerById(String CustomerId) throws GenericException {
		Customer customer = null;

		try {
			ps = connection.prepareStatement(
					"select cust_id,cust_name,cust_gender,cust_email,cust_mobile,timestamp from customer where cust_id=?");
			ps.setString(1, CustomerId);
			ResultSet res = ps.executeQuery();

			while (res.next()) {
				customer = new Customer(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getTimestamp(6));
			}
			if (customer==null) {
				throw new GenericException("failed to get customer");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new GenericException(e.getMessage(), e);
		}

		return customer;
	}

}
