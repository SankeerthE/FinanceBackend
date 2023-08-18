package com.java.servicelayerimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.java.Exceptions.GenericException;
import com.java.daoimpl.DocumentDAOImpl;
import com.java.entities.DocumentBlob;
import com.java.entities.DocumentStr;
import com.java.servicelayer.DocumentService;

public class DocumentServiceImpl implements DocumentService {
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();

	@Override
	public DocumentStr getDocument(String applicationNumber) throws GenericException {
		DocumentBlob documentBlob = null;
		try {
			documentBlob = documentDAOImpl.getDocumentByApplicatonNumber(applicationNumber);
		} catch (GenericException e) {
			throw e;
		}
		StringBuffer aadharbuf = new StringBuffer();
		StringBuffer panbuf = new StringBuffer();
		String temp = null;
		BufferedReader reader;
		DocumentStr documentStr = null;
		try {
			reader = new BufferedReader(new InputStreamReader(documentBlob.getAadhar().getBinaryStream()));
			while ((temp = reader.readLine()) != null) {
				aadharbuf.append(temp);
			}

			reader = new BufferedReader(new InputStreamReader(documentBlob.getPan().getBinaryStream()));
			while ((temp = reader.readLine()) != null) {
				panbuf.append(temp);
			}

			documentStr = new DocumentStr(documentBlob.getDocument_id(), documentBlob.getApplication_number(),
					aadharbuf.toString(), panbuf.toString(), null);
		} catch (IOException e) {
			throw new GenericException(e.getMessage(), e);
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return documentStr;
	}

}
