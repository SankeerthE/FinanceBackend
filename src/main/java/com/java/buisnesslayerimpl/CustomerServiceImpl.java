package com.java.buisnesslayerimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.java.buisnesslayer.CustomerService;
import com.java.daoimpl.AccountDAOImpl;
import com.java.daoimpl.CustomerCredentialsDAOImpl;
import com.java.daoimpl.CustomerDAOImpl;
import com.java.daoimpl.DocumentDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Account;
import com.java.entities.Customer;
import com.java.entities.CustomerCredentials;
import com.java.entities.DocumentBlob;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.requestdto.CustomerLoginDTO;
import com.java.responsedto.CustomerLoginResDTO;
import com.java.responsedto.ProfileDTO;
import com.java.utilities.Status;

public class CustomerServiceImpl implements CustomerService {
	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();
	CustomerDAOImpl customerDAOImol = new CustomerDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();
	CustomerCredentialsDAOImpl customerCredentialsDAOImpl = new CustomerCredentialsDAOImpl();

	@Override
	public boolean addLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) throws SQLException {

		long timestamp = System.currentTimeMillis();
		int randomNumber = (int) (Math.random() * 100000);
		String applicationId = "APP" + timestamp + "-" + randomNumber;

		LoanApplication loanApplication = new LoanApplication(applicationId, CustomerId, createLoanDTO.getLoan_id(),
				createLoanDTO.getAmount(), createLoanDTO.getTenure(), createLoanDTO.getEmi(), Status.INPROGRESS.name(), null);

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

	@Override
	public ArrayList<LoanApplication> getApplicationDetails(String customerId) throws SQLException {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationDAOImpl.getAllApplications();
			loanApplications = (ArrayList<LoanApplication>) loanApplications.stream().filter(application -> {
				return application.getCust_id().equals(customerId);
			}).collect(Collectors.toList());

		} catch (SQLException e) {
			throw e;
		}
		return loanApplications;
	}

	@Override
	public DocumentStr getDocument(String applicationNumber) throws Exception {
		DocumentBlob documentBlob = null;
		try {
			documentBlob = documentDAOImpl.getDocumentById(applicationNumber);
		} catch (SQLException e) {
			throw e;
		}
		StringBuffer aadharbuf = new StringBuffer();
		StringBuffer panbuf = new StringBuffer();
		String temp = null;
		BufferedReader reader;
		DocumentStr documentStr = null;
		try {
			reader = new BufferedReader(new InputStreamReader(documentBlob.getAadhar().getBinaryStream()));
			while ((temp = reader.readLine()) != null) {
				aadharbuf.append(temp);
			}

			reader = new BufferedReader(new InputStreamReader(documentBlob.getPan().getBinaryStream()));
			while ((temp = reader.readLine()) != null) {
				panbuf.append(temp);
			}

			documentStr = new DocumentStr(documentBlob.getDocument_id(), documentBlob.getApplication_number(),
					aadharbuf.toString(), panbuf.toString(), null);
		} catch (SQLException | IOException e) {
			throw e;
		}

		return documentStr;
	}

	@Override
	public ProfileDTO getMyProfile(String customerId) throws SQLException {

		Account account = null;
		Customer customer = null;
		try {
			account = accountDAOImpl.getAccountById(customerId);
			customer = customerDAOImol.getCustomerById(customerId);

		} catch (SQLException e) {
			throw e;
		}

		ProfileDTO profileDTO = new ProfileDTO(customer.getCustomerName(), customer.getCustomerGender(),
				customer.getCustomerEmail(), customer.getCustomerMobile(), account.getAccountNumber(),
				account.getBalance());
		return profileDTO;
	}

	@Override
	public CustomerLoginResDTO verifyCredentials(CustomerLoginDTO customerLoginDTO) throws SQLException {
		CustomerLoginResDTO customerLoginResDTO = null;
		ArrayList<CustomerCredentials> customerCredentials;
		try {
			customerCredentials = customerCredentialsDAOImpl.getAllCredentials();
			customerCredentials = (ArrayList<CustomerCredentials>) customerCredentials.stream().filter(credentials -> {
				return credentials.getUsername().equals(customerLoginDTO.getUsername())
						&& credentials.getPassword().equals(customerLoginDTO.getPassword());
			}).collect(Collectors.toList());

			if (customerCredentials != null) {
				customerLoginResDTO = new CustomerLoginResDTO(customerCredentials.get(0).getCustomerId());
			}
		} catch (SQLException e) {
			throw e;
		}

		return customerLoginResDTO;
	}

}
