package com.java.controller;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.buisnesslayerimpl.CustomerServiceImpl;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.requestdto.CustomerLoginDTO;
import com.java.requestdto.UpdatePasswordDTO;
import com.java.responsedto.CustomerLoginResDTO;
import com.java.responsedto.ProfileDTO;
import com.java.responseentity.Response;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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

		} catch (GenericException e) {
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
		} catch (GenericException e) {
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
		} catch (GenericException e) {
			return new Response<DocumentStr>(e.getMessage(), 400, null);
		}

	}

	@GET
	@Path("/getMyProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ProfileDTO> getMyProfile(@HeaderParam("customerId") String customerId) {
		ProfileDTO profileDTO = null;
		try {
			profileDTO = customerServiceImpl.getMyProfile(customerId);
			return new Response<ProfileDTO>("profile retrived", 200, profileDTO);
		} catch (GenericException e) {
			return new Response<ProfileDTO>(e.getMessage(), 400, profileDTO);

		}

	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<CustomerLoginResDTO> verifyLogin(CustomerLoginDTO customerLoginDTO) {
		CustomerLoginResDTO customerLoginResDTO = null;
		try {
			customerLoginResDTO = customerServiceImpl.verifyCredentials(customerLoginDTO);
			return new Response<CustomerLoginResDTO>("correct credentials", 200, customerLoginResDTO);
		} catch (GenericException e) {
			return new Response<CustomerLoginResDTO>(e.getMessage(), 200, customerLoginResDTO);
		}

	}
	
	@PUT
	@Path("/updateCredentials")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> updateCredentials(UpdatePasswordDTO updatePasswordDTO , @HeaderParam("customerId") String customerId){
		boolean status = false;
		try {
			status = customerServiceImpl.updateCredentials(updatePasswordDTO, customerId);
			return new Response<Boolean>("updation of password successful", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}
	}
	
	@DELETE
	@Path("/withdrawApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> withdrawApplication(@HeaderParam("applicationId") String applicationId){
		boolean status = false;
		try {
			status=customerServiceImpl.withdrawLoanApplication(applicationId);
			return new Response<Boolean>("application withdrawn successfully",200,status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(),400,status);
		}
	}

}
