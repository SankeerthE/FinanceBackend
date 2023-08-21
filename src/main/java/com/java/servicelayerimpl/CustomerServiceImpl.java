package com.java.servicelayerimpl;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.daoimpl.AccountDAOImpl;
import com.java.daoimpl.CredentialsDAOImpl;
import com.java.daoimpl.CustomerDAOImpl;
import com.java.daoimpl.DocumentDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Account;
import com.java.entities.Credentials;
import com.java.entities.Customer;
import com.java.requestdto.CreateCustDTO;
import com.java.responsedto.ProfileDTO;
import com.java.servicelayer.CustomerService;
import com.java.utilities.ServiceUtility;
import com.java.utilities.daoutilities.Role;

public class CustomerServiceImpl implements CustomerService {

	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();
	CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
//	CustomerCredentialsDAOImpl customerCredentialsDAOImpl = new CustomerCredentialsDAOImpl();
	CredentialsDAOImpl credentialsDAOImpl = new CredentialsDAOImpl();

	@Override
	public ProfileDTO getMyProfile(String customerId) throws GenericException {
		Account account = null;
		Customer customer = null;
		try {
			account = accountDAOImpl.getAccountById(customerId);
			customer = customerDAOImpl.getCustomerById(customerId);

		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
		if (account == null || customer == null) {
			return null;
		}

		ProfileDTO profileDTO = new ProfileDTO(customer.getCustomerName(), customer.getCustomerGender(),
				customer.getCustomerEmail(), customer.getCustomerMobile(), account.getAccountNumber(),
				account.getBalance());
		return profileDTO;
	}

	@Override
	public boolean createCustomer(CreateCustDTO createCustDTO) throws GenericException {
		String customerId = ServiceUtility.generateId("CUST");

		Customer customer = new Customer(customerId, createCustDTO.getCustomerName(), createCustDTO.getCustomerGender(),
				createCustDTO.getCustomerEmail(), createCustDTO.getCustomerMobile(), null);
		Credentials credentials = new Credentials(createCustDTO.getUserName(), createCustDTO.getPassword(),
				Role.CUSTOMER.name());

		// creating account for new user
		String accountNumber = ServiceUtility.generateId("ACC");
		Account account = new Account(accountNumber, customerId, 0, null);

		boolean addCredentialsStatus = false;
		boolean status = true;
		try {
			boolean createCustomerstatus = customerDAOImpl.createCustomer(customer);
			System.out.println(createCustomerstatus);
			if (!createCustomerstatus) {
				throw new GenericException(
						"failed to create customer mail" + createCustDTO.getCustomerEmail() + "already exists");
			}

			addCredentialsStatus = credentialsDAOImpl.addCredentials(credentials);
			System.out.println(addCredentialsStatus);

			if (!addCredentialsStatus) {
				customerDAOImpl.deleteCustomer(customerId);
			}
			accountDAOImpl.createAccount(account);

		} catch (GenericException e) {

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
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
	}

}
