package com.java.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java.Exceptions.GenericException;
import com.java.jdbcconn.JdbcApp;
import com.java.requestdto.CustomerLoginDTO;
import com.java.utilities.RandomString;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/authentication")
public class AuthenticationEndpoint {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(CustomerLoginDTO customerLoginDTO) {

        try {
        	
            String username = customerLoginDTO.getUsername();
			String password = customerLoginDTO.getPassword();
			// Authenticate the user using the credentials provided
            authenticate(username , password );
            System.out.println(username);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }      
    }
    
    @DELETE
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logoutUser(@HeaderParam("Authorization") String token) throws Exception {
    	JdbcApp jdbc = new JdbcApp();
    	Connection connection = jdbc.getConnection();
    	PreparedStatement ps = jdbc.getPs();
    	
    	ps=connection.prepareStatement("delete from tokens where token=?");
    	ps.setString(1, token);
    	int res=ps.executeUpdate();
    	if(res==0) {
    		Response.status(Response.Status.EXPECTATION_FAILED).build();
    	}
		return Response.ok("logout succesful").build();
    	
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    	JdbcApp jdbc = new JdbcApp();
    	Connection connection = jdbc.getConnection();
    	PreparedStatement ps = jdbc.getPs();
    	
    	ps = connection.prepareStatement("select username,password from credentials where username=? and password=?");
    	ps.setString(1, username);
    	ps.setString(2, password);
    	
		ResultSet res = ps.executeQuery();
		boolean status=false;
		while(res.next()) {
			status=true;
		}
		if(!status) {
			throw new Exception("ivalied credentials");
		}
		
    }
    private String issueToken(String username) throws GenericException {
    	RandomString token=new RandomString();
    	JdbcApp jdbc = new JdbcApp();
    	Connection connection = jdbc.getConnection();
    	PreparedStatement ps = jdbc.getPs();
    	String valid_token = token.nextString();
    	try {
			ps=connection.prepareStatement("insert into tokens(username,token) values(?,?)");
			ps.setString(1, username);
			ps.setString(2, valid_token);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new GenericException(e.getMessage(),e);
		}
    	System.out.println("issued");
        return valid_token;

    }
}