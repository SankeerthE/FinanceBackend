package com.java.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.LoanApplication;

public interface LoanApplicationDAO {
	ArrayList<LoanApplication> getAllApplications() throws SQLException;
	boolean addLoan(LoanApplication loanApplication) throws SQLException;
	boolean deleteLoan(String applicationNumber);
	boolean updateLoan(String applicationNumber, LoanApplication loanApplication) throws SQLException;
	LoanApplication getLoanApplicationById(String applicationNumber) throws SQLException;
	ArrayList<LoanApplication> getLoanApplicationByStatus(String status) throws SQLException;
}