package com.java.responseentity;

public class Response<T> {
	private String mesage;
	private int statusCode;
	private T responseData;
	public Response() {
		
	}
	public Response(String mesage, int statusCode, T responseData) {
		this.mesage = mesage;
		this.statusCode = statusCode;
		this.responseData = responseData;
	}
	public String getMesage() {
		return mesage;
	}
	public void setMesage(String mesage) {
		this.mesage = mesage;
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