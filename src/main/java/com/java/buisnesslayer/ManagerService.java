package com.java.buisnesslayer;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;

public interface ManagerService {
	ArrayList<LoanApplication> getApplications(String status) throws GenericException;
	boolean approveApplication(ApproveDTO approveDTO) throws GenericException;
	boolean rejectApplication(ApproveDTO approveDTO) throws GenericException;
	ArrayList<LoanApplication> getAllApplications() throws GenericException;
}
