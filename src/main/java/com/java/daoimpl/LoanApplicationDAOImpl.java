package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.dao.LoanApplicationDAO;
import com.java.entities.LoanApplication;
import com.java.jdbcconn.JdbcApp;

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
		return null;
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		}

		return true;
	}

	@Override
	public boolean deleteLoan(String applicationId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateLoan(String applicationId, LoanApplication loanApplication) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LoanApplication getLoanApplicationById(String applicationId) {
		// TODO Auto-generated method stub
		return null;
	}

}
