package com.java.buisnesslayer;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;

public interface ManagerService {
	ArrayList<LoanApplication> getApplications(String status) throws SQLException;
	boolean approveApplication(ApproveDTO approveDTO) throws SQLException;
	boolean rejectApplication(ApproveDTO approveDTO) throws SQLException;
	ArrayList<LoanApplication> getAllApplications() throws SQLException;
}
