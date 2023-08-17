package com.java.buisnesslayerimpl;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.buisnesslayer.ManagerService;
import com.java.daoimpl.AccountDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Account;
import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;
import com.java.utilities.Status;

public class ManagerServiceImpl implements ManagerService {
	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();

	@Override
	public ArrayList<LoanApplication> getApplications(String status) throws SQLException {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationDAOImpl.getLoanApplicationByStatus(status);
		} catch (SQLException e) {
			throw e;
		}

		return loanApplications;
	}

	@Override
	public boolean approveApplication(ApproveDTO approveDTO) throws SQLException {
		boolean status = true;
		LoanApplication loanApplication;
		try {
			loanApplication = loanApplicationDAOImpl.getLoanApplicationById(approveDTO.getApplicationNumber());
			loanApplication.setStatus(Status.APPROVED.name());
			loanApplicationDAOImpl.updateLoan(approveDTO.getApplicationNumber(), loanApplication);

			Account account = accountDAOImpl.getAccountById(approveDTO.getCustomerId());
			account.setBalance(account.getBalance() + loanApplication.getAmount());
			accountDAOImpl.updateAccount(account.getAccountNumber(), account);
		} catch (SQLException e) {
			status = false;
			throw e;
		}

		return status;
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
	public boolean rejectApplication(ApproveDTO approveDTO) throws SQLException {
		boolean status = true;
		LoanApplication loanApplication;
		try {
			loanApplication = loanApplicationDAOImpl.getLoanApplicationById(approveDTO.getApplicationNumber());
			loanApplication.setStatus(Status.REJECTED.name());
			loanApplicationDAOImpl.updateLoan(approveDTO.getApplicationNumber(), loanApplication);

		} catch (SQLException e) {
			status = false;
			throw e;
		}

		return status;
	}

}
