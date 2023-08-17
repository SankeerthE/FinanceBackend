package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.dao.LoanApplicationDAO;
import com.java.entities.LoanApplication;
import com.java.jdbcconn.JdbcApp;
import com.java.utilities.Status;

public class LoanApplicationDAOImpl implements LoanApplicationDAO {

	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public ArrayList<LoanApplication> getAllApplications() throws SQLException {
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
			throw e;
		}
		return loanApplications;
	}

	@Override
	public boolean addLoan(LoanApplication loanApplication) throws SQLException {

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
			if(res==0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		}

		return true;
	}

	@Override
	public boolean deleteLoan(String applicationNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateLoan(String applicationNumber, LoanApplication loanApplication) throws SQLException {
		
		try {
			ps=connection.prepareStatement("update loanapplication set status=? where application_number=?");
			ps.setString(1, loanApplication.getStatus());
			ps.setString(2, applicationNumber);
			int res = ps.executeUpdate();
			if(res==0) {
				return false;
			}
		} catch (SQLException e) {
			throw e;
		}
		return true;
	}

	@Override
	public LoanApplication getLoanApplicationById(String applicationNumber) throws SQLException {

		LoanApplication loanApplication = null;
		try {
			ps=connection.prepareStatement("select application_number,cust_id,loan_id,amount,tenure,emi,status,timestamp from loanapplication where application_number=?");
			ps.setString(1, applicationNumber);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				return null;
			}
			while(res.next()) {
				loanApplication = new LoanApplication(res.getString(1), res.getString(2), res.getString(3),
						res.getDouble(4), res.getInt(5), res.getDouble(6), res.getString(7), res.getTimestamp(8));
			}
		} catch (SQLException e) {
			throw e;
		}
		return loanApplication;
	}

	@Override
	public ArrayList<LoanApplication> getLoanApplicationByStatus(String status) throws SQLException {

		ArrayList<LoanApplication> loanApplications = new ArrayList<>();
		try {
			ps = connection.prepareStatement(
					"select application_number,cust_id,loan_id,amount,tenure,emi,status,timestamp from loanapplication where status=?");
			ps.setString(1, status);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				return null;
			}
			while (res.next()) {
				loanApplications.add(new LoanApplication(res.getString(1), res.getString(2), res.getString(3),
						res.getDouble(4), res.getInt(5), res.getDouble(6), res.getString(7), res.getTimestamp(8)));
			}
			
		} catch (SQLException e) {
			throw e;
		}

		return loanApplications;
	}

}
