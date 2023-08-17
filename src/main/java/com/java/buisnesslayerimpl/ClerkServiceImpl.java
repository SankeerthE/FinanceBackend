package com.java.buisnesslayerimpl;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.buisnesslayer.ClerkService;
import com.java.daoimpl.CustomerCredentialsDAOImpl;
import com.java.daoimpl.CustomerDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Customer;
import com.java.entities.CustomerCredentials;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;

public class ClerkServiceImpl implements ClerkService {
	CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
	CustomerCredentialsDAOImpl customerCredentialsDAOImpl = new CustomerCredentialsDAOImpl();
	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();

	@Override
	public boolean createCustomer(CreateCustDTO createCustDTO) throws SQLException {
		// TODO Auto-generated method stub\
		long timestamp = System.currentTimeMillis();
		int randomNumber = (int) (Math.random() * 100000);
		String customerId = "CUST" + timestamp + "-" + randomNumber;

		Customer customer = new Customer(customerId, createCustDTO.getCustomerName(), createCustDTO.getCustomerGender(),
				createCustDTO.getCustomerEmail(), createCustDTO.getCustomerMobile(), null);
		CustomerCredentials customerCredentials = new CustomerCredentials(customerId, createCustDTO.getUserName(),
				createCustDTO.getPassword());

//		boolean createCustomerStatus = false;
		boolean addCredentialsStatus = false;
		try {
			customerDAOImpl.createCustomer(customer);
			addCredentialsStatus = customerCredentialsDAOImpl.addCredentials(customerCredentials);
			System.out.println(addCredentialsStatus);
			return true;

		} catch (SQLException e) {
			if (!addCredentialsStatus) {
				customerDAOImpl.deleteCustomer(customerId);
			}
			throw e;
		}

	}

	@Override
	public ArrayList<Customer> getAllCustomers() {
		try {
			ArrayList<Customer> customers = customerDAOImpl.getAllCustomers();
			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ArrayList<LoanApplication> getAllApplications() {
		try {
			ArrayList<LoanApplication> loanApplications = loanApplicationDAOImpl.getAllApplications();
			return loanApplications;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
