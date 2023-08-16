package com.java.buisnesslayerimpl;

import java.sql.SQLException;

import com.java.buisnesslayer.CustomerService;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.LoanApplication;
import com.java.requestdto.CreateLoanDTO;

public class CustomerServiceImpl implements CustomerService {
	LoanApplicationDAOImpl loanApplicationDAOImpl=new LoanApplicationDAOImpl();

	@Override
	public boolean addLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) {
		
		long timestamp = System.currentTimeMillis();
        int randomNumber = (int) (Math.random() * 100000); 
        String applicationId="APP"+timestamp+"-"+randomNumber;
		
		LoanApplication loanApplication=new LoanApplication(applicationId,CustomerId,createLoanDTO.getLoan_id(),
				createLoanDTO.getAmount(), createLoanDTO.getTenure(), createLoanDTO.getEmi(), "under progress",null);
		try {
			loanApplicationDAOImpl.addLoan(loanApplication);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
