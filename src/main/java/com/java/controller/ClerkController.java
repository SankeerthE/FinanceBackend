package com.java.controller;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.buisnesslayerimpl.ClerkServiceImpl;
import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;
import com.java.requestdto.CreateLoanDTO;
import com.java.responseentity.Response;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/clerk")
public class ClerkController {
	ClerkServiceImpl clerkServiceImpl = new ClerkServiceImpl();
	
	@POST
	@Path("/createCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> createCustomer(CreateCustDTO createCustDTO) {
		boolean status = false;
		try {
			status = clerkServiceImpl.createCustomer(createCustDTO);
			return new Response<Boolean>("customer created successfully", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}

	}

	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<Customer>> getAllCustomers() {
		ArrayList<Customer> customers = null;
		try {
			customers = clerkServiceImpl.getAllCustomers();
			return new Response<ArrayList<Customer>>("retrived all the customers", 200, customers);
		} catch (GenericException e) {
			return new Response<ArrayList<Customer>>(e.getMessage(), 400, customers);
		}

	}

	@GET
	@Path("/getAllApplicaitons")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getAllApplications() {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = clerkServiceImpl.getAllApplications();
			return new Response<ArrayList<LoanApplication>>("retrived all the applications", 200, loanApplications);
		} catch (GenericException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);

		}
		
	}
	
	@POST
	@Path("/createLoanApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> createLoanApplication(CreateLoanDTO createLoanDTO, @HeaderParam("customerId") String customerId){
		boolean status = false;
		try {
			status = clerkServiceImpl.createLoanApplication(createLoanDTO, customerId);
			return new Response<Boolean>("created loan application", 200, status);

		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}
	}

}
