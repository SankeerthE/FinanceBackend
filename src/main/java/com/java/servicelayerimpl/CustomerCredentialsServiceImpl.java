package com.java.servicelayerimpl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.java.Exceptions.GenericException;
import com.java.daoimpl.CustomerCredentialsDAOImpl;
import com.java.entities.CustomerCredentials;
import com.java.requestdto.CustomerLoginDTO;
import com.java.requestdto.UpdatePasswordDTO;
import com.java.responsedto.CustomerLoginResDTO;
import com.java.servicelayer.CustomerCredentialsService;

public class CustomerCredentialsServiceImpl implements CustomerCredentialsService {
	
	CustomerCredentialsDAOImpl customerCredentialsDAOImpl = new CustomerCredentialsDAOImpl();

	@Override
	public CustomerLoginResDTO verifyCredentials(CustomerLoginDTO customerLoginDTO) throws GenericException {
		CustomerLoginResDTO customerLoginResDTO = null;
		ArrayList<CustomerCredentials> customerCredentials;
		try {
			customerCredentials = customerCredentialsDAOImpl.getAllCredentials();
			customerCredentials = (ArrayList<CustomerCredentials>) customerCredentials.stream().filter(credentials -> {
				return credentials.getUsername().equals(customerLoginDTO.getUsername())
						&& credentials.getPassword().equals(customerLoginDTO.getPassword());
			}).collect(Collectors.toList());

			if (customerCredentials != null) {
				customerLoginResDTO = new CustomerLoginResDTO(customerCredentials.get(0).getCustomerId());
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
			updationStatus = customerCredentialsDAOImpl.updateCredentials(customerId,
					updatePasswordDTO.getOldPassword(), updatePasswordDTO.getNewPassword());
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return updationStatus;
	}

}
