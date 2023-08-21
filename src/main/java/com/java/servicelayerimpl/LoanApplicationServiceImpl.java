package com.java.servicelayerimpl;

import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.java.Exceptions.GenericException;
import com.java.daoimpl.AccountDAOImpl;
import com.java.daoimpl.DocumentDAOImpl;
import com.java.daoimpl.LoanApplicationDAOImpl;
import com.java.entities.Account;
import com.java.entities.DocumentStr;
import com.java.entities.LoanApplication;
import com.java.requestdto.ApproveDTO;
import com.java.requestdto.CreateLoanDTO;
import com.java.servicelayer.LoanApplicationService;
import com.java.utilities.ServiceUtility;
import com.java.utilities.daoutilities.Status;

public class LoanApplicationServiceImpl implements LoanApplicationService {

	LoanApplicationDAOImpl loanApplicationDAOImpl = new LoanApplicationDAOImpl();
	DocumentDAOImpl documentDAOImpl = new DocumentDAOImpl();
	AccountDAOImpl accountDAOImpl = new AccountDAOImpl();

	@Override
	public ArrayList<LoanApplication> getAllApplications() throws GenericException {
		try {
			ArrayList<LoanApplication> loanApplications = loanApplicationDAOImpl.getAllApplications();
			return loanApplications;
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
	}

	@Override
	public boolean createLoanApplication(CreateLoanDTO createLoanDTO, String CustomerId) throws GenericException {
		String applicationId = ServiceUtility.generateId("APP");

		LoanApplication loanApplication = new LoanApplication(applicationId, CustomerId, createLoanDTO.getLoan_id(),
				createLoanDTO.getAmount(), createLoanDTO.getTenure(), createLoanDTO.getEmi(), Status.INPROGRESS.name(),
				null);

		String documentId = ServiceUtility.generateId("DOC");
		DocumentStr document = new DocumentStr(documentId, applicationId, createLoanDTO.getAadhar(),
				createLoanDTO.getPan(), null);
		boolean status = true;
		try {
			loanApplicationDAOImpl.addLoan(loanApplication);
			documentDAOImpl.addDocument(document);
		} catch (GenericException e) {
			status = false;
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public boolean withdrawLoanApplication(String applicationNumber) throws GenericException {
		boolean deletionStatus = false;
		try {
			LoanApplication loanApplication = loanApplicationDAOImpl.getLoanApplicationById(applicationNumber);
			String loanStatus = loanApplication.getStatus();
			if (loanStatus.equals(Status.INPROGRESS.name())) {
				deletionStatus = documentDAOImpl.deleteDocument(applicationNumber);
				if (deletionStatus) {
					deletionStatus = loanApplicationDAOImpl.deleteLoan(applicationNumber);
					if (!deletionStatus) {
						throw new GenericException("failed to delete loan application");
					}
				} else {
					throw new GenericException("failed to delete document");
				}
			} else {
				throw new GenericException("applicaiton has been approved or rejected");
			}

		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
		return deletionStatus;
	}

	@Override
	public ArrayList<LoanApplication> getApplicationDetails(String customerId) throws GenericException {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationDAOImpl.getAllApplications();
			loanApplications = (ArrayList<LoanApplication>) loanApplications.stream().filter(application -> {
				return application.getCust_id().equals(customerId);
			}).collect(Collectors.toList());

		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
		return loanApplications;
	}

	@Override
	public boolean approveApplication(ApproveDTO approveDTO) throws GenericException {
		boolean status = true;
		LoanApplication loanApplication;
		try {
			loanApplication = loanApplicationDAOImpl.getLoanApplicationById(approveDTO.getApplicationNumber());
			loanApplication.setStatus(Status.APPROVED.name());
			loanApplicationDAOImpl.updateLoan(approveDTO.getApplicationNumber(), loanApplication);

			Account account = accountDAOImpl.getAccountById(approveDTO.getCustomerId());
			account.setBalance(account.getBalance() + loanApplication.getAmount());
			accountDAOImpl.updateAccount(account.getAccountNumber(), account);
			// sending email
//			sendEmail("sankeerthmeda@gmail.com", "hello", "body");
			String host = "smtp.gmail.com";
            final String username = "manishssssskumaraaaaa@gmail.com"; // Replace with your Gmail email
            final String password = "osbzpdwcdbexonng"; // Replace with your Gmail password

//			final String username="sankeerthmeda2903@gmail.com";
//			final String password = "Sankeerthmeda@2903";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
    		props.put("mail.smtp.socketFactory.class",
    				"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
    		props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
    		props.put("mail.smtp.port", "465"); //SMTP Port
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            
            //session.setDebug(true);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sankeerthmeda@gmail.com"));
            message.setSubject("New Login Credentials");
            message.setText("Hello user your application:"+approveDTO.getApplicationNumber()+", got APPROVED");
            
//            UserCredentialsDataAccess ucdao = new UserCredentialsDataAccess();
//			Boolean flag = ucdao.update(userCredentials.getUsername(), userCredentials.getPassword(), loginpassword, 0);
			Transport.send(message);
            System.out.println("Email sent successfully.");
		} catch (GenericException e) {
			status = false;
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public boolean rejectApplication(ApproveDTO approveDTO) throws GenericException {
		boolean status = true;
		LoanApplication loanApplication;
		try {
			loanApplication = loanApplicationDAOImpl.getLoanApplicationById(approveDTO.getApplicationNumber());
			loanApplication.setStatus(Status.REJECTED.name());
			loanApplicationDAOImpl.updateLoan(approveDTO.getApplicationNumber(), loanApplication);

		} catch (GenericException e) {
			status = false;
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return status;
	}

	@Override
	public ArrayList<LoanApplication> getApplications(String status) throws GenericException {
		ArrayList<LoanApplication> loanApplications = null;
		try {
			loanApplications = loanApplicationDAOImpl.getLoanApplicationByStatus(status);
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}

		return loanApplications;
	}

	@Override
	public boolean tickleEmi() throws GenericException {
		boolean status=false;

		ArrayList<LoanApplication> loanApplications;
		try {
			loanApplications = loanApplicationDAOImpl.getLoanApplicationByStatus(Status.APPROVED.name());
			for (LoanApplication loanApplication : loanApplications) {
				status=false;
				Account account = accountDAOImpl.getAccountById(loanApplication.getCust_id());
				account.setBalance(account.getBalance() - loanApplication.getEmi());
				accountDAOImpl.updateAccount(account.getAccountNumber(), account);
				status=true;
			}
		} catch (GenericException e) {
			throw e;
		} catch (Exception e) {
			throw new GenericException(e.getMessage(), e);
		}
		return status;
	}
	
	public void sendEmail(String to, String subject, String content) throws Exception{
        // Set up properties for the mail session
		Properties properties = new Properties();
    	properties.put("mail.smtp.host", "smtp.office365.com");
    	properties.put("mail.smtp.port", "587");
    	properties.put("mail.smtp.auth", "true");
    	properties.put("mail.smtp.starttls.enable", "true");
    	properties.put("mail.smtp.ssl.trust", "smtp.office365.com");
//    	properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        // Create a mail session with authentication
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("sankeerthandvardhan@outlook.com", "OracleProject");
                    }
                });
        try {
            // Create a new MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sankeerthandvardhan@outlook.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);
            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
