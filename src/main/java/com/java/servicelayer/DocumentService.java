package com.java.servicelayer;

import com.java.Exceptions.GenericException;
import com.java.entities.DocumentStr;

public interface DocumentService {
	DocumentStr getDocument(String applicationNumber) throws GenericException;

}
