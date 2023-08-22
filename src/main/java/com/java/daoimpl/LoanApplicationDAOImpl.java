package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.dao.LoanApplicationDAO;
import com.java.entities.LoanApplication;
import com.java.jdbcconn.JdbcApp;

public class LoanApplicationDAOImpl implements LoanApplicationDAO {

	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public ArrayList<LoanApplication> getAllApplications() throws GenericException {
		ArrayList<LoanApplication> loanApplications = new ArrayList<LoanApplication>();
		try {
			ps = connection.prepareStatement(
					"select application_number,cust_id,loan_id,amount,tenure,emi,status,timestamp from loanapplication");
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				loanApplications.add(new LoanApplication(res.getString(1), res.getString(2), res.getString(3),
						res.getDouble(4), res.getInt(5), res.getDouble(6), res.getString(7), res.getTimestamp(8)));
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return loanApplications;
	}

	@Override
	public boolean addLoan(LoanApplication loanApplication) throws GenericException {
		boolean status = false;

		try {
			ps = connection.prepareStatement(
					"INSERT INTO loanapplication(application_number,cust_id,loan_id,amount,tenure,emi,status,timestamp) "
							+ "values(?,?,?,?,?,?,?,CURRENT_TIMESTAMP)");
			ps.setString(1, loanApplication.getApplication_number());
			ps.setString(2, loanApplication.getCust_id());
			ps.setString(3, loanApplication.getLoan_id());
			ps.setDouble(4, loanApplication.getAmount());
			ps.setInt(5, loanApplication.getTenure());
			ps.setDouble(6, loanApplication.getEmi());
			ps.setString(7, loanApplication.getStatus());
			int res = ps.executeUpdate();
			status=true;
			if (res == 0) {
				throw new GenericException("failed to add loan application");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public boolean deleteLoan(String applicationNumber) throws GenericException {
		boolean status=true;
		try {
			ps=connection.prepareStatement("delete from loanapplication where application_number=?");
			ps.setString(1, applicationNumber);
			System.out.println("ds");
			int res = ps.executeUpdate();
			System.out.println("dsdss");

			if(res==0) {
				status = false;
				throw new GenericException("failed to delete loan application");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(),e);
		}
//		System.out.println("dsdss");

		return status;
	}

	@Override
	public boolean updateLoan(String applicationNumber, LoanApplication loanApplication) throws GenericException {
		boolean status = true;
		try {
			ps = connection.prepareStatement("update loanapplication set status=? where application_number=?");
			ps.setString(1, loanApplication.getStatus());
			ps.setString(2, applicationNumber);
			int res = ps.executeUpdate();
			System.out.println(res);
			if (res == 0) {
				System.out.println("entered");
				status = false;
				throw new GenericException("failed to update loan");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public LoanApplication getLoanApplicationById(String applicationNumber) throws GenericException {

		LoanApplication loanApplication = null;
		try {
			ps = connection.prepareStatement(
					"select application_number,cust_id,loan_id,amount,tenure,emi,status,timestamp from loanapplication where application_number=?");
			ps.setString(1, applicationNumber);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				throw new GenericException("failed to get loan application");
			}
			while (res.next()) {
				loanApplication = new LoanApplication(res.getString(1), res.getString(2), res.getString(3),
						res.getDouble(4), res.getInt(5), res.getDouble(6), res.getString(7), res.getTimestamp(8));
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return loanApplication;
	}

	@Override
	public ArrayList<LoanApplication> getLoanApplicationByStatus(String status) throws GenericException {

		ArrayList<LoanApplication> loanApplications = new ArrayList<>();
		try {
			ps = connection.prepareStatement(
					"select application_number,cust_id,loan_id,amount,tenure,emi,status,timestamp from loanapplication where status=?");
			ps.setString(1, status);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				throw new GenericException("failed to get loan application for status: " + status);
			}
			while (res.next()) {
				loanApplications.add(new LoanApplication(res.getString(1), res.getString(2), res.getString(3),
						res.getDouble(4), res.getInt(5), res.getDouble(6), res.getString(7), res.getTimestamp(8)));
			}

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return loanApplications;
	}

}
