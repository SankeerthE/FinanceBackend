package com.java.servicelayerimpl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.java.Exceptions.GenericException;
import com.java.daoimpl.AccountDAOImpl;
import com.java.daoimpl.DocumentDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Account;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;
import com.java.requestdto.CreateLoanDTO;
import com.java.servicelayer.LoanApplicationService;
import com.java.utilities.ServiceUtility;
import com.java.utilities.Status;

public class LoanApplicationServiceImpl implements LoanApplicationService {

	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();


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

	@Override
	public boolean withdrawLoanApplication(String applicationNumber) throws GenericException {
		boolean deletionStatus = false;
		try {
			LoanApplication loanApplication = loanApplicationDAOImpl.getLoanApplicationById(applicationNumber);
			String loanStatus = loanApplication.getStatus();
			if (loanStatus.equals(Status.INPROGRESS.name())) {
				deletionStatus = documentDAOImpl.deleteDocument(applicationNumber);
				if (deletionStatus) {
					deletionStatus = loanApplicationDAOImpl.deleteLoan(applicationNumber);
					if (!deletionStatus) {
						throw new GenericException("failed to delete loan application");
					}
				} else {
					throw new GenericException("failed to delete document");
				}
			} else {
				throw new GenericException("applicaiton has been approved or rejected");
			}

		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
		return deletionStatus;
	}

	@Override
	public ArrayList<LoanApplication> getApplicationDetails(String customerId) throws GenericException {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationDAOImpl.getAllApplications();
			loanApplications = (ArrayList<LoanApplication>) loanApplications.stream().filter(application -> {
				return application.getCust_id().equals(customerId);
			}).collect(Collectors.toList());

		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
		return loanApplications;
	}

	@Override
	public boolean approveApplication(ApproveDTO approveDTO) throws GenericException {
		boolean status = true;
		LoanApplication loanApplication;
		try {
			loanApplication = loanApplicationDAOImpl.getLoanApplicationById(approveDTO.getApplicationNumber());
			loanApplication.setStatus(Status.APPROVED.name());
			loanApplicationDAOImpl.updateLoan(approveDTO.getApplicationNumber(), loanApplication);

			Account account = accountDAOImpl.getAccountById(approveDTO.getCustomerId());
			account.setBalance(account.getBalance() + loanApplication.getAmount());
			accountDAOImpl.updateAccount(account.getAccountNumber(), account);
		} catch (GenericException e) {
			status = false;
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public boolean rejectApplication(ApproveDTO approveDTO) throws GenericException {
		boolean status = true;
		LoanApplication loanApplication;
		try {
			loanApplication = loanApplicationDAOImpl.getLoanApplicationById(approveDTO.getApplicationNumber());
			loanApplication.setStatus(Status.REJECTED.name());
			loanApplicationDAOImpl.updateLoan(approveDTO.getApplicationNumber(), loanApplication);

		} catch (GenericException e) {
			status = false;
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public ArrayList<LoanApplication> getApplications(String status) throws GenericException {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationDAOImpl.getLoanApplicationByStatus(status);
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return loanApplications;
	}

}
