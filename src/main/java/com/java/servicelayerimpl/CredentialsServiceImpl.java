package com.java.servicelayerimpl;

import java.util.ArrayList;
import java.util.Optional;
import com.java.Exceptions.GenericException;
import com.java.daoimpl.CredentialsDAOImpl;
import com.java.daoimpl.CustomerDAOImpl;
import com.java.entities.Credentials;
import com.java.entities.Customer;
import com.java.requestdto.CustomerLoginDTO;
import com.java.requestdto.UpdatePasswordDTO;
import com.java.responsedto.CustomerLoginResDTO;
import com.java.servicelayer.CredentialsService;

public class CredentialsServiceImpl implements CredentialsService {
	
	CredentialsDAOImpl credentialsDAOImpl = new CredentialsDAOImpl();
	CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();

	@Override
	public CustomerLoginResDTO verifyCredentials(CustomerLoginDTO customerLoginDTO) throws GenericException {
		CustomerLoginResDTO customerLoginResDTO = null;
		try {
			
			Credentials credential = credentialsDAOImpl.getCredentialsByName(customerLoginDTO.getUsername()); 

			if (credential != null) {
				ArrayList<Customer> customers = new ArrayList<Customer>();
				customers=customerDAOImpl.getAllCustomers();
				Optional<Customer> customer = customers.stream().filter(cust->{
					return cust.getCustomerEmail().equals(customerLoginDTO.getUsername());
				}).findAny();
				if(customer.isEmpty()) {
					throw new GenericException("did not found customer");
				}
				customerLoginResDTO = new CustomerLoginResDTO(customer.get().getCustomerId());
			}
			else {
				throw new GenericException("invalid credentials");
			}
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return customerLoginResDTO;
	}

	@Override
	public boolean updateCredentials(UpdatePasswordDTO updatePasswordDTO, String customerId) throws GenericException {
		boolean updationStatus = false;
		try {
			Customer customer = customerDAOImpl.getCustomerById(customerId);
			updationStatus = credentialsDAOImpl.updateCredentials(customer.getCustomerEmail(),
					updatePasswordDTO.getOldPassword(), updatePasswordDTO.getNewPassword());
			if(updatePasswordDTO.getOldPassword().equals(updatePasswordDTO.getNewPassword())) {
				throw new GenericException("New password cannot be same as Old password");
			}
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return updationStatus;
	}

}
