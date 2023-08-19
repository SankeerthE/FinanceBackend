package com.java.responseentity;

public class Response<T> {
	private String message;
	private int statusCode;
	private T responseData;
	public Response() {
		
	}
	public Response(String message, int statusCode, T responseData) {
		this.message = message;
		this.statusCode = statusCode;
		this.responseData = responseData;
	}
	public String getMesage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public T getResponseData() {
		return responseData;
	}
	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}
	
}