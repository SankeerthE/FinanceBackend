package com.java.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.LoanApplication;

public interface LoanApplicationDAO {
	ArrayList<LoanApplication> getAllApplications() throws SQLException;
	boolean addLoan(LoanApplication loanApplication) throws SQLException;
	boolean deleteLoan(String applicationId);
	boolean updateLoan(String applicationId, LoanApplication loanApplication);
	LoanApplication getLoanApplicationById(String applicationId);
}
