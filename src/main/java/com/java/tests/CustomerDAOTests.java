package com.java.tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.java.Exceptions.GenericException;
import com.java.daoimpl.CustomerDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Customer;
import com.java.entities.LoanApplication;

public class CustomerDAOTests {
	private static CustomerDAOImpl customerDAOImpl;
	private static LoanApplicationDAOImpl loanApplicationDAOImpl;

	@BeforeClass
	public static void initialize() {
		customerDAOImpl = new CustomerDAOImpl();
		loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	}

	@AfterClass
	public static void cleanUp() {
		customerDAOImpl = null;
		loanApplicationDAOImpl = null;
	}

//	@Test
	public void getCustomerByValidId() {
		try {
			Customer actualCustomer = customerDAOImpl.getCustomerById("CUST1692456653903-36258");
			Customer expectedCustomer = new Customer("CUST1692456653903-36258", "kanna", "female", "balu@oracle.com",
					"74367678360", null);
			Assert.assertEquals(expectedCustomer.getCustomerName(), actualCustomer.getCustomerName());
			Assert.assertEquals(expectedCustomer.getCustomerGender(), actualCustomer.getCustomerGender());
			Assert.assertEquals(expectedCustomer.getCustomerEmail(), actualCustomer.getCustomerEmail());
			Assert.assertEquals(expectedCustomer.getCustomerMobile(), actualCustomer.getCustomerMobile());

		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	@Test
	public void getCustomerByInvalidId() {
		try {
			Customer actualCustomer = customerDAOImpl.getCustomerById("CUST1692456653903-3625");
			Customer expectedCustomer = null;
			Assert.assertEquals(expectedCustomer, actualCustomer);
		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	@Test
	public void getCustomerByNullId() {
		try {
			customerDAOImpl.getCustomerById(null);
//				System.out.println("hehdh");
		} catch (GenericException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Assert.assertEquals("failed to get customer", e.getMessage());

		}

	}

//	@Test
	public void addLoanApplicationByValidApplicationNumber() {
		LoanApplication loanApplication = new LoanApplication("APP1692263964103-93222", "CUST1692263792074-58854",
				"loanid", 4400, 33, 9, "INPROGRESS", null);
		try {
			boolean actualStatus = loanApplicationDAOImpl.addLoan(loanApplication);
			boolean expectedStatus = true;
			Assert.assertEquals(expectedStatus, actualStatus);
		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
	public void addLoanApplicationByRepeatedApplicationNumber() {
		LoanApplication loanApplication = new LoanApplication("APP1692263964103-93222", "CUST1692263792074-58854",
				"loanid", 4400, 33, 9, "INPROGRESS", null);
		try {
			loanApplicationDAOImpl.addLoan(loanApplication);
			
		} catch (GenericException e) {
			System.out.println(e.getMessage());
			Assert.assertEquals("ORA-00001: unique constraint (SCOTT.SYS_C007619) violated\n", e.getMessage());
		}
	}

//	@Test
	public void addLoanApplicationByIncorrectLoanId() {
		LoanApplication loanApplication = new LoanApplication("APP1692263964103-91122", "CUST1692263792074-58854",
				"loand", 4400, 33, 9, "INPROGRESS", null);
		try {
			loanApplicationDAOImpl.addLoan(loanApplication);

		} catch (GenericException e) {
			System.out.println(e.getMessage());
			Assert.assertEquals("ORA-02291: integrity constraint (SCOTT.SYS_C007621) violated - parent key not found\n",
					e.getMessage().toString());
		}
	}
	
//	@Test
	public void deleteLoanApplicationByExistingApplicationNumber() {
		try {
			boolean actualStatus=loanApplicationDAOImpl.deleteLoan("APP1692263964103-93222");
			boolean expectedStatus = true;
			System.out.println(actualStatus);
			Assert.assertEquals(expectedStatus, actualStatus);
		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void deleteLoanApplicationByNonExistingApplicationNumber() {
		try {
			boolean actualStatus=loanApplicationDAOImpl.deleteLoan("APP1692263964103-93222");
			
		} catch (GenericException e) {
			System.out.println(e.getMessage());
			Assert.assertEquals("failed to delete loan application", e.getMessage());
		}
	}
	
//	@Test
//	@Ignore
	public void insertCustomerPostiveTest() {
		try {
			boolean actualBoolean = customerDAOImpl.createCustomer(new Customer("CUST1692456653903-36250",
					"miriyala vardhan", "male", "vardha@oracle.com", "987654321", null));
			boolean expectedboolean = true;
			System.out.println(actualBoolean);
			Assert.assertEquals(expectedboolean, actualBoolean);

		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	@Test
	public void updateLoanPositiveTest() {
		try {
			boolean actualBoolean = loanApplicationDAOImpl.updateLoan("APP1692263964103-93322", new LoanApplication("APP1692263964103-93322", "CUST1692263792074-58854",
					"loanid", 4400, 33, 9, "REJECTED", null));
			boolean expectedboolean = true;
			Assert.assertEquals(expectedboolean, actualBoolean);
		} catch (GenericException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateLoanNegativeTest() {
		try {
			boolean actualBoolean = loanApplicationDAOImpl.updateLoan("APP1692263964103-92", new LoanApplication("APP1692263964103-93322", "CUST1692263792074-58854",
					"loand", 4400, 33, 9, "REJECTED", null));
		} catch (GenericException e) {
			System.out.println(e.getMessage());

			Assert.assertEquals("failed to update loan", e.getMessage());
			
		}
	}
	
}