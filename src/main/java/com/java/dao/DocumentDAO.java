package com.java.dao;

import com.java.Exceptions.GenericException;
import com.java.entities.DocumentBlob;
import com.java.entities.DocumentStr;

public interface DocumentDAO {
	boolean addDocument(DocumentStr document) throws GenericException;

	DocumentStr getAllDocuments();

	boolean updateDocument(String documentId);

	boolean deleteDocument(String documentId);

	DocumentBlob getDocumentById(String documentId) throws GenericException;
}
