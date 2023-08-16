package com.java.controller;

import com.java.buisnesslayerimpl.ClerkServiceImpl;
import com.java.requestdto.CreateCustDTO;
import com.java.responseentity.Response;

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
	
}
