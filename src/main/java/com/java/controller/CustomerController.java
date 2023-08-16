package com.java.controller;

import com.java.buisnesslayerimpl.CustomerServiceImpl;
import com.java.requestdto.CreateLoanDTO;
import com.java.responseentity.Response;

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
	public Response<Boolean> addLoanApplication(CreateLoanDTO createLoanDTO, @HeaderParam("customerId") String customerId) {
		
		boolean status = customerServiceImpl.addLoanApplication(createLoanDTO, customerId);
		if(status) {
			return new Response<Boolean>("created loan application",200,status);
		}else {
			return new Response<Boolean>("creation failed",400,status);
		}
	}
}
