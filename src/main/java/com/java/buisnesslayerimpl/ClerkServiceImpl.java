package com.java.buisnesslayerimpl;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.buisnesslayer.ClerkService;
import com.java.daoimpl.AccountDAOImpl;
import com.java.daoimpl.CustomerCredentialsDAOImpl;
import com.java.daoimpl.CustomerDAOImpl;
import com.java.daoimpl.DocumentDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Account;
import com.java.entities.Customer;
import com.java.entities.CustomerCredentials;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;
import com.java.requestdto.CreateLoanDTO;

public class ClerkServiceImpl implements ClerkService {
	CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
	CustomerCredentialsDAOImpl customerCredentialsDAOImpl = new CustomerCredentialsDAOImpl();
	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();

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

		// creating account for new user
		timestamp = System.currentTimeMillis();
		randomNumber = (int) (Math.random() * 100000);
		String accountNumber = "ACC" + timestamp + "-" + randomNumber;
		Account account = new Account(accountNumber, customerId, 0, null);

//		boolean createCustomerStatus = false;
		boolean addCredentialsStatus = false;
		boolean status = true;
		try {
			customerDAOImpl.createCustomer(customer);
			addCredentialsStatus = customerCredentialsDAOImpl.addCredentials(customerCredentials);
			accountDAOImpl.createAccount(account);

		} catch (SQLException e) {
			if (!addCredentialsStatus) {
				customerDAOImpl.deleteCustomer(customerId);
			}
			status = false;
			throw e;
		}
		return status;

	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws SQLException {
		try {
			ArrayList<Customer> customers = customerDAOImpl.getAllCustomers();
			return customers;
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public ArrayList<LoanApplication> getAllApplications() throws SQLException {
		try {
			ArrayList<LoanApplication> loanApplications = loanApplicationDAOImpl.getAllApplications();
			return loanApplications;
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public boolean createLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) throws SQLException {
		long timestamp = System.currentTimeMillis();
		int randomNumber = (int) (Math.random() * 100000);
		String applicationId = "APP" + timestamp + "-" + randomNumber;

		LoanApplication loanApplication = new LoanApplication(applicationId, CustomerId, createLoanDTO.getLoan_id(),
				createLoanDTO.getAmount(), createLoanDTO.getTenure(), createLoanDTO.getEmi(), "under progress", null);

		String documentId = "DOC" + timestamp + "-" + randomNumber;
		DocumentStr document = new DocumentStr(documentId, applicationId, createLoanDTO.getAadhar(),
				createLoanDTO.getPan(), null);
		boolean status = true;
		try {
			loanApplicationDAOImpl.addLoan(loanApplication);
			documentDAOImpl.addDocument(document);
		} catch (SQLException e) {
			status = false;
			throw e;
		}

		return status;
	}

}
