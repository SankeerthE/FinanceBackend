package com.java.buisnesslayerimpl;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
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
import com.java.utilities.ServiceUtility;
import com.java.utilities.Status;

public class ClerkServiceImpl implements ClerkService {
	CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
	CustomerCredentialsDAOImpl customerCredentialsDAOImpl = new CustomerCredentialsDAOImpl();
	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();

	@Override
	public boolean createCustomer(CreateCustDTO createCustDTO) throws GenericException {
		String customerId = ServiceUtility.generateId("CUST");

		Customer customer = new Customer(customerId, createCustDTO.getCustomerName(), createCustDTO.getCustomerGender(),
				createCustDTO.getCustomerEmail(), createCustDTO.getCustomerMobile(), null);
		CustomerCredentials customerCredentials = new CustomerCredentials(customerId, createCustDTO.getUserName(),
				createCustDTO.getPassword());

		// creating account for new user
		String accountNumber = ServiceUtility.generateId("ACC");
		Account account = new Account(accountNumber, customerId, 0, null);

		boolean addCredentialsStatus = false;
		boolean status = true;
		try {
			customerDAOImpl.createCustomer(customer);
			addCredentialsStatus = customerCredentialsDAOImpl.addCredentials(customerCredentials);
			accountDAOImpl.createAccount(account);

		} catch (GenericException e) {
			if (!addCredentialsStatus) {
				customerDAOImpl.deleteCustomer(customerId);
			}
			status = false;
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
		return status;

	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws GenericException {
		try {
			ArrayList<Customer> customers = customerDAOImpl.getAllCustomers();
			return customers;
		} catch (GenericException e) {
			throw new GenericException(e.getMessage(), e);
		}
	}

	@Override
	public ArrayList<LoanApplication> getAllApplications() throws GenericException {
		try {
			ArrayList<LoanApplication> loanApplications = loanApplicationDAOImpl.getAllApplications();
			return loanApplications;
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
	}

	@Override
	public boolean createLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) throws GenericException {

		String applicationId = ServiceUtility.generateId("APP");

		LoanApplication loanApplication = new LoanApplication(applicationId, CustomerId, createLoanDTO.getLoan_id(),
				createLoanDTO.getAmount(), createLoanDTO.getTenure(), createLoanDTO.getEmi(), Status.INPROGRESS.name(),
				null);

		String documentId = ServiceUtility.generateId("DOC");
		DocumentStr document = new DocumentStr(documentId, applicationId, createLoanDTO.getAadhar(),
				createLoanDTO.getPan(), null);
		boolean status = true;
		try {
			loanApplicationDAOImpl.addLoan(loanApplication);
			documentDAOImpl.addDocument(document);
		} catch (GenericException e) {
			status = false;
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

}
