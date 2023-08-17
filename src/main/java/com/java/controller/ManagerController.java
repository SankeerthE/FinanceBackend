package com.java.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.java.buisnesslayerimpl.ManagerServiceImpl;
import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;
import com.java.responseentity.Response;
import com.java.utilities.Status;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/manager")
public class ManagerController {
	ManagerServiceImpl managerServiceImpl = new ManagerServiceImpl();

	@GET
	@Path("/waitingForApproval")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getWaitingForApproval() {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = managerServiceImpl.getApplications(Status.INPROGRESS.toString());
			return new Response<ArrayList<LoanApplication>>("feteched waiting for approval applications successfully",
					200, loanApplications);
		} catch (SQLException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);
		}
	}

	@GET
	@Path("/getApproved")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getApproved() {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = managerServiceImpl.getApplications(Status.APPROVED.toString());
			return new Response<ArrayList<LoanApplication>>("feteched waiting for approval applications successfully",
					200, loanApplications);
		} catch (SQLException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);
		}
	}

	@GET
	@Path("/getRejected")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getRejected() {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = managerServiceImpl.getApplications(Status.REJECTED.toString());
			return new Response<ArrayList<LoanApplication>>("feteched waiting for approval applications successfully",
					200, loanApplications);
		} catch (SQLException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);
		}
	}
	
	@GET
	@Path("/getAllApplicaitons")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getAllApplications() {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = managerServiceImpl.getAllApplications();
			return new Response<ArrayList<LoanApplication>>("retrived all the applications", 200, loanApplications);
		} catch (SQLException e) {
			return new Response<ArrayList<LoanApplication>>(e.getMessage(), 400, loanApplications);

		}
		
	}
	
	@POST
	@Path("/onApprove")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> approveApplication(ApproveDTO approveDTO){
		boolean status=false;
		try {
			status=managerServiceImpl.approveApplication(approveDTO);
			return new Response<Boolean>("application got approved",200,status);
		} catch (SQLException e) {
			return new Response<Boolean>(e.getMessage(),400,status);
		}
	}
	
	@POST
	@Path("/onReject")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<Boolean> rejectApplication(ApproveDTO approveDTO){
		boolean status=false;
		try {
			status=managerServiceImpl.rejectApplication(approveDTO);
			return new Response<Boolean>("application got rejected",200,status);
		} catch (SQLException e) {
			return new Response<Boolean>(e.getMessage(),400,status);
		}
	}
	

}
