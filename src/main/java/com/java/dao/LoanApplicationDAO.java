package com.java.dao;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.LoanApplication;

public interface LoanApplicationDAO {
	ArrayList<LoanApplication> getAllApplications() throws GenericException;
	boolean addLoan(LoanApplication loanApplication) throws GenericException;
	boolean deleteLoan(String applicationNumber) throws GenericException;
	boolean updateLoan(String applicationNumber, LoanApplication loanApplication) throws GenericException;
	LoanApplication getLoanApplicationById(String applicationNumber) throws GenericException;
	ArrayList<LoanApplication> getLoanApplicationByStatus(String status) throws GenericException;
}