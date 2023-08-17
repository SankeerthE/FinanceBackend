package com.java.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.buisnesslayerimpl.CustomerServiceImpl;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.requestdto.ProfileReqDTO;
import com.java.responsedto.ProfileDTO;
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

		boolean status = false;
		try {
			status = customerServiceImpl.addLoanApplication(createLoanDTO, customerId);
			return new Response<Boolean>("created loan application", 200, status);

		} catch (SQLException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}

	}

	@GET
	@Path("/getMyApplications")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getMyApplications(@HeaderParam("customerId") String customerId) {
		ArrayList<LoanApplication> loanApplications;
		try {
			loanApplications = customerServiceImpl.getApplicationDetails(customerId);
			return new Response<ArrayList<LoanApplication>>("got data", 200, loanApplications);
		} catch (SQLException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, null);
		}

	}

	@GET
	@Path("/getDocument")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<DocumentStr> getDocument(@HeaderParam("applicationId") String applicationId) {
		DocumentStr documentStr;
		try {
			documentStr = customerServiceImpl.getDocument(applicationId);
			return new Response<DocumentStr>("document retrived", 200, documentStr);
		} catch (Exception e) {
			return new Response<DocumentStr>(e.getMessage(), 400, null);
		}

	}

	@GET
	@Path("/getMyProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ProfileDTO> getMyProfile(@HeaderParam("customerId") String customerId) {
		ProfileDTO profileDTO;
		try {
			profileDTO = customerServiceImpl.getMyProfile(customerId);
			return new Response<ProfileDTO>("profile retrived", 200, profileDTO);
		} catch (SQLException e) {
			return new Response<ProfileDTO>(e.getMessage(), 400, null);

		}

	}

}
