package com.java.buisnesslayerimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.java.buisnesslayer.CustomerService;
import com.java.daoimpl.AccountDAOImpl;
import com.java.daoimpl.CustomerDAOImpl;
import com.java.daoimpl.DocumentDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Account;
import com.java.entities.Customer;
import com.java.entities.DocumentBlob;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.responsedto.ProfileDTO;

public class CustomerServiceImpl implements CustomerService {
	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();
	CustomerDAOImpl customerDAOImol = new CustomerDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();

	@Override
	public boolean addLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) {

		long timestamp = System.currentTimeMillis();
		int randomNumber = (int) (Math.random() * 100000);
		String applicationId = "APP" + timestamp + "-" + randomNumber;

		LoanApplication loanApplication = new LoanApplication(applicationId, CustomerId, createLoanDTO.getLoan_id(),
				createLoanDTO.getAmount(), createLoanDTO.getTenure(), createLoanDTO.getEmi(), "under progress", null);

		String documentId = "DOC" + timestamp + "-" + randomNumber;
		DocumentStr document = new DocumentStr(documentId, applicationId, createLoanDTO.getAadhar(),
				createLoanDTO.getPan(), null);
		try {
			loanApplicationDAOImpl.addLoan(loanApplication);
			documentDAOImpl.addDocument(document);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public ArrayList<LoanApplication> getApplicationDetails(String customerId) {

		try {
			ArrayList<LoanApplication> loanApplications = loanApplicationDAOImpl.getAllApplications();
			loanApplications = (ArrayList<LoanApplication>) loanApplications.stream().filter(application -> {
				return application.getCust_id().equals(customerId);
			}).collect(Collectors.toList());
			return loanApplications;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public DocumentStr getDocument(String applicationNumber) {
		DocumentBlob documentBlob = null;
		try {
			documentBlob = documentDAOImpl.getDocumentById(applicationNumber);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer aadharbuf = new StringBuffer();
		StringBuffer panbuf = new StringBuffer();
		String temp = null;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(documentBlob.getAadhar().getBinaryStream()));
			while ((temp = reader.readLine()) != null) {
				aadharbuf.append(temp);
			}

			reader = new BufferedReader(new InputStreamReader(documentBlob.getPan().getBinaryStream()));
			while ((temp = reader.readLine()) != null) {
				panbuf.append(temp);
			}

			DocumentStr documentStr = new DocumentStr(documentBlob.getDocument_id(),
					documentBlob.getApplication_number(), aadharbuf.toString(), panbuf.toString(), null);
			return documentStr;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ProfileDTO getMyProfile(String customerId, String accountNumber) {

		Account account = null;
		Customer customer = null;
		try {
			account = accountDAOImpl.getAccountById(accountNumber);
			customer = customerDAOImol.getCustomerById(customerId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ProfileDTO profileDTO = new ProfileDTO(customer.getCustomerName(), customer.getCustomerGender(),
				customer.getCustomerEmail(), customer.getCustomerMobile(), account.getAccountNumber(),
				account.getBalance());
		return profileDTO;
	}

}
