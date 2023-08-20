package com.java.servicelayer;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;
import com.java.requestdto.CreateLoanDTO;

public interface LoanApplicationService {
	ArrayList<LoanApplication> getAllApplications() throws GenericException;

	boolean createLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) throws GenericException;

	boolean withdrawLoanApplication(String applicationNumber) throws GenericException;

	ArrayList<LoanApplication> getApplicationDetails(String customerId) throws GenericException;
	
	boolean approveApplication(ApproveDTO approveDTO) throws GenericException;

	boolean rejectApplication(ApproveDTO approveDTO) throws GenericException;

	ArrayList<LoanApplication> getApplications(String status) throws GenericException;
	
	boolean tickleEmi() throws GenericException;


}
