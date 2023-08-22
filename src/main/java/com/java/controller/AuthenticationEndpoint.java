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
            String actualRole = getRole(username,password);
            String expectedRole = customerLoginDTO.getUserRole();
//            System.out.println(actualRole+" "+expectedRole);
            if(!actualRole.equals(expectedRole)) {
            	throw new Exception(expectedRole+" is unauthorized to LOGIN");
            }

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            String id=getId(username);
        	
            return Response.ok(token+":"+id+":"+actualRole).build();

        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }      
    }
    
    private String getRole(String username, String password) throws Exception {
    	String role = null;
    	
    	JdbcApp jdbc = new JdbcApp();
    	Connection connection = jdbc.getConnection();
    	PreparedStatement ps = jdbc.getPs();
    	
    	ps = connection.prepareStatement("select role from credentials where username=? and password=?");
    	ps.setString(1, username);
    	ps.setString(2, password);
    	
		ResultSet res = ps.executeQuery();
		boolean status=false;
		while(res.next()) {
			role = res.getString(1);
			status=true;
		}
		if(!status) {
			throw new Exception("invalid credentials");
		}
		return role;
	}

	private String getId(String username) throws Exception{
    	
    	JdbcApp jdbc = new JdbcApp();
    	Connection connection = jdbc.getConnection();
    	PreparedStatement ps = jdbc.getPs();
    	
    	ps=connection.prepareStatement("select cust_id,cust_name,cust_gender,cust_email,cust_mobile,timestamp from customer where cust_email=?");
    	ps.setString(1, username);
    	ResultSet res=ps.executeQuery();
    	
    	String id=null;
		while(res.next()) {
			id=res.getString(1);
		}
		return id;
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
