package com.java.buisnesslayer;

import java.util.ArrayList;

import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.responsedto.ProfileDTO;

public interface CustomerService {
	boolean addLoanApplication(CreateLoanDTO createLoanDTO, String customerId);
	ArrayList<LoanApplication> getApplicationDetails(String customerId);
	DocumentStr getDocument(String applicationNumber);
	ProfileDTO getMyProfile(String customerId, String accountNumber);
}
