package com.java.controller;

import java.util.ArrayList;

import com.java.buisnesslayerimpl.ClerkServiceImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Customer;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateCustDTO;
import com.java.responseentity.Response;

import jakarta.ws.rs.GET;
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
	public Response<Boolean> createCustomer(CreateCustDTO createCustDTO){
		boolean status = clerkServiceImpl.createCustomer(createCustDTO);
		if(status) {
			return new Response<Boolean>("customer created successfully",200,status);
		}else {
			return new Response<Boolean>("failed to create customer",400,status);
		}
	}
	
	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<Customer>> getAllCustomers(){
		ArrayList<Customer> customers=clerkServiceImpl.getAllCustomers();
		if(customers!=null) {
			return new Response<ArrayList<Customer>>("retrived all the customers",200,customers);
		}else {
			return new Response<ArrayList<Customer>>("failed to retrive the customers",400,customers);
		}
	}
	
	@GET
	@Path("getAllApplicaitons")
	@Produces(MediaType.APPLICATION_JSON)
	public Response<ArrayList<LoanApplication>> getAllApplications(){
		ArrayList<LoanApplication> loanApplications = clerkServiceImpl.getAllApplications();
		if(loanApplications!=null) {
			return new Response<ArrayList<LoanApplication>>("retrived all the applications",200,loanApplications);
		}else {
			return new Response<ArrayList<LoanApplication>>("failed to retrive applications",400,loanApplications);
		}
	}
	
}
