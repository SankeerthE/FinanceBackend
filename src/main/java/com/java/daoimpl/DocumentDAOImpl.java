package com.java.daoimpl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.Exceptions.GenericException;
import com.java.dao.DocumentDAO;
import com.java.entities.DocumentBlob;
import com.java.entities.DocumentStr;
import com.java.jdbcconn.JdbcApp;

public class DocumentDAOImpl implements DocumentDAO {

	JdbcApp jdbc = new JdbcApp();
	Connection connection = jdbc.getConnection();
	PreparedStatement ps = jdbc.getPs();

	@Override
	public boolean addDocument(DocumentStr document) throws GenericException {
		boolean status=true;
		try {
			ps = connection
					.prepareStatement("insert into document(document_id,application_number,aadhar,pan,timestamp) "
							+ "values(?,?,?,?,CURRENT_TIMESTAMP)");
			Blob aadharBlob = connection.createBlob();
			aadharBlob.setBytes(1, document.getAadhar().getBytes());

			Blob panBlob = connection.createBlob();
			panBlob.setBytes(1, document.getPan().getBytes());

			ps.setString(1, document.getDocument_id());
			ps.setString(2, document.getApplication_number());
			ps.setBlob(3, aadharBlob);
			ps.setBlob(4, panBlob);
			int res = ps.executeUpdate();
			if(res==0) {
				status=false;
				throw new GenericException("failed to add document");
			}

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public DocumentStr getAllDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateDocument(String documentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDocument(String applicationNumber) throws GenericException {
		boolean status=true;
		try {
			ps=connection.prepareStatement("delete from document where application_number=?");
			ps.setString(1, applicationNumber);
			int res=ps.executeUpdate();
			if(res==0) {
				status=false;
				throw new GenericException("failed to delete document");
			}
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(),e);
		}
		return status;
	}

	@Override
	public DocumentBlob getDocumentByApplicatonNumber(String applicationId) throws GenericException {
		try {
			ps = connection.prepareStatement(
					"select document_id,application_number,aadhar,pan,timestamp from document where application_number=?");
			ps.setString(1, applicationId);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				return new DocumentBlob(res.getString(1), res.getString(2), res.getBlob(3), res.getBlob(4),
						res.getTimestamp(5));
			}

		} catch (SQLException e) {
			throw new GenericException(e.getMessage(), e);
		}
		return null;
	}

}
