package com.java.controller;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;
import com.java.requestdto.CreateLoanDTO;
import com.java.responseentity.Response;
import com.java.servicelayerimpl.CustomerServiceImpl;
import com.java.servicelayerimpl.LoanApplicationServiceImpl;
import com.java.utilities.Secured;
import com.java.utilities.daoutilities.Role;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/clerk")
public class ClerkController {
	CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
	LoanApplicationServiceImpl loanApplicationServiceImpl = new LoanApplicationServiceImpl();

	@POST
	@Secured({ Role.CLERK })
	@Path("/createCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> createCustomer(CreateCustDTO createCustDTO, @HeaderParam("role") String role) {
		boolean status = false;
		try {
			status = customerServiceImpl.createCustomer(createCustDTO);
			return new Response<Boolean>("customer created successfully", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}

	}

	@GET
	@Secured({ Role.CLERK })
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<Customer>> getAllCustomers(@HeaderParam("role") String role) {
		ArrayList<Customer> customers = null;
		try {
			customers = customerServiceImpl.getAllCustomers();
			return new Response<ArrayList<Customer>>("retrived all the customers", 200, customers);
		} catch (GenericException e) {
			return new Response<ArrayList<Customer>>(e.getMessage(), 400, customers);
		}

	}

	@GET
	@Secured({ Role.CLERK })
	@Path("/getAllApplicaitons")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getAllApplications(@HeaderParam("role") String role) {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationServiceImpl.getAllApplications();
			return new Response<ArrayList<LoanApplication>>("retrived all the applications", 200, loanApplications);
		} catch (GenericException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);

		}

	}

	@POST
	@Secured({ Role.CLERK })
	@Path("/createLoanApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> createLoanApplication(CreateLoanDTO createLoanDTO,
			@HeaderParam("customerId") String customerId, @HeaderParam("role") String role) {
		boolean status = false;
		try {
			status = loanApplicationServiceImpl.createLoanApplication(createLoanDTO, customerId);
			return new Response<Boolean>("created loan application", 200, status);

		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}
	}

}
