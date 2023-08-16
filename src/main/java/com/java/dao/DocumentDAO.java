package com.java.dao;

import java.sql.SQLException;

import com.java.entities.DocumentBlob;
import com.java.entities.DocumentStr;

public interface DocumentDAO {
	boolean addDocument(DocumentStr document) throws SQLException;

	DocumentStr getAllDocuments();

	boolean updateDocument(String documentId);

	boolean deleteDocument(String documentId);

	DocumentBlob getDocumentById(String documentId) throws SQLException;
}
