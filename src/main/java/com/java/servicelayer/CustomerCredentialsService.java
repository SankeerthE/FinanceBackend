package com.java.servicelayer;

import com.java.Exceptions.GenericException;
import com.java.requestdto.CustomerLoginDTO;
import com.java.requestdto.UpdatePasswordDTO;
import com.java.responsedto.CustomerLoginResDTO;

public interface CustomerCredentialsService {
	CustomerLoginResDTO verifyCredentials(CustomerLoginDTO customerLoginDTO) throws GenericException;

	boolean updateCredentials(UpdatePasswordDTO updatePasswordDTO, String customerId) throws GenericException;

}
