package com.java.controller;

import java.util.ArrayList;

import com.java.Exceptions.GenericException;
import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;
import com.java.responseentity.Response;
import com.java.servicelayerimpl.LoanApplicationServiceImpl;
import com.java.utilities.Secured;
import com.java.utilities.daoutilities.Role;
import com.java.utilities.daoutilities.Status;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/manager")
public class ManagerController {
	LoanApplicationServiceImpl loanApplicationServiceImpl = new LoanApplicationServiceImpl();

	@GET
	@Secured({ Role.MANAGER })
	@Path("/waitingForApproval")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getWaitingForApproval(@HeaderParam("role") String role) {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationServiceImpl.getApplications(Status.INPROGRESS.toString());
			return new Response<ArrayList<LoanApplication>>("feteched waiting for approval applications successfully",
					200, loanApplications);
		} catch (GenericException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);
		}
	}

	@GET
	@Secured({ Role.MANAGER })
	@Path("/getApproved")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getApproved(@HeaderParam("role") String role) {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationServiceImpl.getApplications(Status.APPROVED.toString());
			return new Response<ArrayList<LoanApplication>>("feteched waiting for approval applications successfully",
					200, loanApplications);
		} catch (GenericException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);
		}
	}

	@GET
	@Secured({ Role.MANAGER })
	@Path("/getRejected")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getRejected(@HeaderParam("role") String role) {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationServiceImpl.getApplications(Status.REJECTED.toString());
			return new Response<ArrayList<LoanApplication>>("feteched waiting for approval applications successfully",
					200, loanApplications);
		} catch (GenericException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);
		}
	}

	@GET
	@Secured({ Role.MANAGER })
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
	@Secured({ Role.MANAGER })
	@Path("/onApprove")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> approveApplication(ApproveDTO approveDTO, @HeaderParam("role") String role) {
		boolean status = false;
		try {
			status = loanApplicationServiceImpl.approveApplication(approveDTO);
			return new Response<Boolean>("application got approved", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}
	}

	@POST
	@Secured({ Role.MANAGER })
	@Path("/onReject")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> rejectApplication(ApproveDTO approveDTO, @HeaderParam("role") String role) {
		boolean status = false;
		try {
			status = loanApplicationServiceImpl.rejectApplication(approveDTO);
			return new Response<Boolean>("application got rejected", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>(e.getMessage(), 400, status);
		}
	}

	@GET
	@Secured({ Role.MANAGER })
	@Path("/tickleEmi")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> tickleEmi(@HeaderParam("role") String role) {
		boolean status = false;
		try {
			status = loanApplicationServiceImpl.tickleEmi();
			return new Response<Boolean>("successfully deducted emi's", 200, status);
		} catch (GenericException e) {
			return new Response<Boolean>("failed to deduct emi's", 400, status);
		}
	}

}
