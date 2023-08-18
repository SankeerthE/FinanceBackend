package com.java.buisnesslayer;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.requestdto.CustomerLoginDTO;
import com.java.requestdto.UpdatePasswordDTO;
import com.java.responsedto.CustomerLoginResDTO;
import com.java.responsedto.ProfileDTO;

public interface CustomerService {
	boolean addLoanApplication(CreateLoanDTO createLoanDTO, String customerId) throws GenericException;

	ArrayList<LoanApplication> getApplicationDetails(String customerId) throws GenericException;

	DocumentStr getDocument(String applicationNumber) throws Exception;

	ProfileDTO getMyProfile(String customerId) throws GenericException;

	CustomerLoginResDTO verifyCredentials(CustomerLoginDTO customerLoginDTO) throws GenericException;

	boolean updateCredentials(UpdatePasswordDTO updatePasswordDTO , String customerId) throws GenericException;
}
