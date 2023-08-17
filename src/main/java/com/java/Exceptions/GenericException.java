package com.java.Exceptions;

@SuppressWarnings("serial")
public class GenericException extends Exception {
	public GenericException() {
		super("Age is less than 18");
	}

	public GenericException(String errorMessage) {
		super(errorMessage);
	}

	public GenericException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}
}
