package com.java.controller;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;
import com.java.requestdto.CustomerLoginDTO;
import com.java.requestdto.UpdatePasswordDTO;
import com.java.responsedto.CustomerLoginResDTO;
import com.java.responsedto.ProfileDTO;
import com.java.responseentity.Response;
import com.java.servicelayerimpl.CredentialsServiceImpl;
import com.java.servicelayerimpl.CustomerServiceImpl;
import com.java.servicelayerimpl.DocumentServiceImpl;
import com.java.servicelayerimpl.LoanApplicationServiceImpl;
import com.java.utilities.Secured;
import com.java.utilities.daoutilities.Role;

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
	LoanApplicationServiceImpl loanApplicationServiceImpl = new LoanApplicationServiceImpl();
	DocumentServiceImpl documentServiceImpl = new DocumentServiceImpl();
	CredentialsServiceImpl credentialsServiceImpl = new CredentialsServiceImpl();

	@POST
	@Secured({ Role.CUSTOMER })
	@Path("/addLoanApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> addLoanApplication(CreateLoanDTO createLoanDTO,
			@HeaderParam("customerId") String customerId, @HeaderParam("role") String role) {

		boolean status = false;
		try {
			status = loanApplicationServiceImpl.createLoanApplication(createLoanDTO, customerId);
			return new Response<Boolean>("created loan application", 200, status);

		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}

	}

	@GET
	@Secured({ Role.CUSTOMER })
	@Path("/getMyApplications")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getMyApplications(@HeaderParam("customerId") String customerId,
			@HeaderParam("role") String role) {
		ArrayList<LoanApplication> loanApplications;
		try {
			loanApplications = loanApplicationServiceImpl.getApplicationDetails(customerId);
			return new Response<ArrayList<LoanApplication>>("got data", 200, loanApplications);
		} catch (GenericException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, null);
		}

	}

	@GET
	@Secured({ Role.CUSTOMER, Role.CLERK, Role.MANAGER })
	@Path("/getDocument")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<DocumentStr> getDocument(@HeaderParam("applicationId") String applicationId,
			@HeaderParam("role") String role) {
		DocumentStr documentStr;
		try {
			documentStr = documentServiceImpl.getDocument(applicationId);
			return new Response<DocumentStr>("document retrived", 200, documentStr);
		} catch (GenericException e) {
			return new Response<DocumentStr>(e.getMessage(), 400, null);
		}

	}

	@GET
	@Secured({ Role.CUSTOMER })
	@Path("/getMyProfile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ProfileDTO> getMyProfile(@HeaderParam("customerId") String customerId,
			@HeaderParam("role") String role) {
		ProfileDTO profileDTO = null;
		try {
			profileDTO = customerServiceImpl.getMyProfile(customerId);
			return new Response<ProfileDTO>("profile retrived", 200, profileDTO);
		} catch (GenericException e) {
			return new Response<ProfileDTO>(e.getMessage(), 400, profileDTO);

		}

	}

	@POST
	@Secured({ Role.CUSTOMER })
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<CustomerLoginResDTO> verifyLogin(CustomerLoginDTO customerLoginDTO,
			@HeaderParam("role") String role) {
		CustomerLoginResDTO customerLoginResDTO = null;
		try {
			customerLoginResDTO = credentialsServiceImpl.verifyCredentials(customerLoginDTO);
			return new Response<CustomerLoginResDTO>("correct credentials", 200, customerLoginResDTO);
		} catch (GenericException e) {
			return new Response<CustomerLoginResDTO>(e.getMessage(), 200, customerLoginResDTO);
		}

	}

	@PUT
	@Secured({ Role.CUSTOMER })
	@Path("/updateCredentials")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> updateCredentials(UpdatePasswordDTO updatePasswordDTO,
			@HeaderParam("customerId") String customerId, @HeaderParam("role") String role) {
		boolean status = false;
		try {
			status = credentialsServiceImpl.updateCredentials(updatePasswordDTO, customerId);
			return new Response<Boolean>("updation of password successful", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}
	}

	@DELETE
	@Secured({ Role.CUSTOMER })
	@Path("/withdrawApplication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> withdrawApplication(@HeaderParam("applicationId") String applicationId,
			@HeaderParam("role") String role) {
		boolean status = false;
		try {
			status = loanApplicationServiceImpl.withdrawLoanApplication(applicationId);
			return new Response<Boolean>("application withdrawn successfully", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}
	}

}
