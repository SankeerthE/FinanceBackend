package com.java.buisnesslayer;

import com.java.requestdto.CreateLoanDTO;

public interface CustomerService {
	boolean addLoanApplication(CreateLoanDTO createLoanDTO, String customerId);
}
