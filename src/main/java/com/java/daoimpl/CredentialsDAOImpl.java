package com.java.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.dao.CredentialsDAO;
import com.java.entities.Credentials;
import com.java.jdbcconn.JdbcApp;

public class CredentialsDAOImpl implements CredentialsDAO {
	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean addCredentials(Credentials credentials) throws GenericException {
		boolean status = true;
		try {
			ps = connection.prepareStatement("insert into credentials(username,password,role) values(?,?,?)");
			ps.setString(1, credentials.getUsername());
			ps.setString(2, credentials.getPassword());
			ps.setString(3, credentials.getRole());
			int res = ps.executeUpdate();
			if (res == 0) {
				status = false;
				throw new GenericException("failed to add credentials");

			}
		} catch (SQLException e) {
			status = false;
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public boolean updateCredentials(String username, String oldPassword, String newPassword) throws GenericException {
		boolean status = true;
		try {
			ps = connection.prepareStatement("update credentials set password=? where username=? and password=?");
			ps.setString(1, newPassword);
			ps.setString(2, username);
			ps.setString(3, oldPassword);
			int res = ps.executeUpdate();
//			System.out.println(res+" "+username+" "+oldPassword+" "+newPassword);
			if (res == 0) {
				status = false;
				throw new GenericException("incorrect password");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public boolean deleteCredentials(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Credentials getCredentialsByName(String username) throws GenericException {
		Credentials credentials = null;
		try {
			ps = connection
					.prepareStatement("select username,password,role from credentials where username=?");
			ps.setString(1, username);
			ResultSet res = ps.executeQuery();

			if (res.getFetchSize() == 0) {
				throw new GenericException("there are no credentials for " + username);
			}
			while (res.next()) {
				credentials = new Credentials(res.getString(1), res.getString(2), res.getString(3));
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return credentials;
	}

	@Override
	public ArrayList<Credentials> getAllCredentials() throws GenericException {
		ArrayList<Credentials> credentials = new ArrayList<Credentials>();
		try {
			ps = connection.prepareStatement("select username,password,role from credentials");
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				credentials.add(new Credentials(res.getString(1), res.getString(2), res.getString(3)));
			}

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}

		return credentials;
	}

}
