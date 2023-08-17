package com.java.buisnesslayer;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.requestdto.CustomerLoginDTO;
import com.java.responsedto.CustomerLoginResDTO;
import com.java.responsedto.ProfileDTO;

public interface CustomerService {
	boolean addLoanApplication(CreateLoanDTO createLoanDTO, String customerId) throws SQLException;
	ArrayList<LoanApplication> getApplicationDetails(String customerId) throws SQLException;
	DocumentStr getDocument(String applicationNumber) throws Exception;
	ProfileDTO getMyProfile(String customerId) throws SQLException;
	CustomerLoginResDTO verifyCredentials(CustomerLoginDTO customerLoginDTO) throws SQLException;
}
