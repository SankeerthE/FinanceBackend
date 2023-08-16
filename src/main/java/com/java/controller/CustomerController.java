package com.java.controller;

import java.util.ArrayList;

import com.java.buisnesslayerimpl.CustomerServiceImpl;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.requestdto.DocumentDTO;
import com.java.responseentity.Response;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/customer")
public class CustomerController {
	CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

	@POST
	@Path("/addLoanApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> addLoanApplication(CreateLoanDTO createLoanDTO,
			@HeaderParam("customerId") String customerId) {

		boolean status = customerServiceImpl.addLoanApplication(createLoanDTO, customerId);
		if (status) {
			return new Response<Boolean>("created loan application", 200, status);
		} else {
			return new Response<Boolean>("creation failed", 400, status);
		}
	}

	@GET
	@Path("/getMyApplications")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getMyApplications(@HeaderParam("customerId") String customerId) {
		ArrayList<LoanApplication> loanApplications = customerServiceImpl.getApplicationDetails(customerId);
		if (loanApplications != null) {
			return new Response<ArrayList<LoanApplication>>("got data", 200, loanApplications);
		} else {
			return new Response<ArrayList<LoanApplication>>("did not got data", 400, null);
		}
	}

	@GET
	@Path("/getDocument")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<DocumentStr> getDocument(DocumentDTO documentDTO) {
		DocumentStr documentStr = customerServiceImpl.getDocument(documentDTO.getApplicationId());
		if (documentStr != null) {
			return new Response<DocumentStr>("document retrived", 200, documentStr);
		} else {
			return new Response<DocumentStr>("document not retrived", 400, null);
		}
	}
}
