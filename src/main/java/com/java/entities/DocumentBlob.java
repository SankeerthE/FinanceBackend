package com.java.entities;

import java.sql.Blob;
import java.sql.Timestamp;

public class DocumentBlob {
	private String document_id;
	private String application_number;
	private Blob aadhar;
	private Blob pan;
	private Timestamp timestamp;

	public DocumentBlob(String document_id, String application_number, Blob aadhar, Blob pan, Timestamp timestamp) {
		super();
		this.document_id = document_id;
		this.application_number = application_number;
		this.aadhar = aadhar;
		this.pan = pan;
		this.timestamp = timestamp;
	}

	public String getDocument_id() {
		return document_id;
	}

	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}

	public String getApplication_number() {
		return application_number;
	}

	public void setApplication_number(String application_number) {
		this.application_number = application_number;
	}

	public Blob getAadhar() {
		return aadhar;
	}

	public void setAadhar(Blob aadhar) {
		this.aadhar = aadhar;
	}

	public Blob getPan() {
		return pan;
	}

	public void setPan(Blob pan) {
		this.pan = pan;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
