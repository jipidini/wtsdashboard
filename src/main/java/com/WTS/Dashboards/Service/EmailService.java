package com.WTS.Dashboards.Service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Utility.SMSutility;
import com.sun.mail.smtp.SMTPTransport;

@Service
@PropertySource("classpath:application.properties")
@ConfigurationProperties("spring.mail")
public class EmailService {
	@Value("${spring.mail.smtp.host}")
	private String smtpHost;
	
	@Value("${spring.mail.smtp.port}")
	private String smtpPort;
	
	@Value("${spring.mail.username}")
	private String smtpUser;
	
	@Value("${spring.mail.password}")
	private String smtpPwd;
	
	@Value("${spring.mail.smtp.auth}")
	private String smtpAuth;
	
	@Value("${spring.mail.smtp.starttls.enable}")
	private String smtpTls;
	
	@Lob
	@Value("${spring.mail.smtp.text.newEta}")
	private String mailtextfornewEta;
	

	@Lob
	@Value("${spring.mail.smtp.text.redalert}")
	private String mailtextforRedAlert;
	@Value("${spring.mail.smtp.to}")
	private String to;
	@Value("${spring.mail.smtp.subject}")
	private String subject;
	public String getSmtpHost() {
		return smtpHost;
	}

	
	@Value("${cgi.dashboard.sms.host}")
	private String smsHost;
	
	@Value("${cgi.dashboard.sms.port}")
	private String smsPort;
	
	@Value("${cgi.dashboard.sms.username}")
	private String smsUserName;
	
	@Value("${cgi.dashboard.sms.password}")
	private String smsPassword;
	
	@Value("${cgi.dashboard.sms.newEta.text}")
	private String smsETAText;
	
	@Value("${cgi.dashboard.sms.redalert.text}")
	private String smsRedText;
	
	@Value("${cgi.dashboard.sms.disable}")
	private String smsDisable;
	
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPwd() {
		return smtpPwd;
	}

	public void setSmtpPwd(String smtpPwd) {
		this.smtpPwd = smtpPwd;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getSmtpTls() {
		return smtpTls;
	}

	public void setSmtpTls(String smtpTls) {
		this.smtpTls = smtpTls;
	}
	
	public String getMailtextfornewEta() {
		return mailtextfornewEta;
	}

	public void setMailtextfornewEta(String mailtextfornewEta) {
		this.mailtextfornewEta = mailtextfornewEta;
	}

	public String getMailtextforRedAlert() {
		return mailtextforRedAlert;
	}

	public void setMailtextforRedAlert(String mailtextforRedAlert) {
		this.mailtextforRedAlert = mailtextforRedAlert;
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void SendMailAlertNewEta(String Emailid)
	{
		
		

			Properties props = new Properties();
			props.put("mail.smtp.auth",getSmtpAuth());
			props.put("mail.smtp.starttls.enable",getSmtpTls());
			props.put("mail.smtp.host",getSmtpHost());
			props.put("mail.smtp.port",getSmtpPort());

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(getSmtpUser(),getSmtpPwd());
				}
			  });

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(getSmtpUser()));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(Emailid));
				message.setSubject(getSubject());
				message.setText(getMailtextfornewEta());

				Transport.send(message);

				System.out.println("Done");
				session.getTransport().close();
			    }catch(Exception ex){
			    	ex.printStackTrace();
			    }finally {
			    }
			    	
			    }
	
	public void sendMailRedAlert(String Emailid)
	{
		
		

			Properties props = new Properties();
			props.put("mail.smtp.auth",getSmtpAuth());
			props.put("mail.smtp.starttls.enable",getSmtpTls());
			props.put("mail.smtp.host",getSmtpHost());
			props.put("mail.smtp.port",getSmtpPort());

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(getSmtpUser(),getSmtpPwd());
				}
			  });

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(getSmtpUser()));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(Emailid));
				message.setSubject(getSubject());
				message.setText(getMailtextforRedAlert());

				Transport.send(message);

				System.out.println("Done");
				System.out.println("Done");
				session.getTransport().close();
			    }catch(Exception ex){
			    	ex.printStackTrace();
			    }finally {
			    }
			    	
			    }
	
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

	    mailSender.setHost(getSmtpHost());
	    mailSender.setPort(Integer.valueOf(getSmtpPort()));
	     
	    mailSender.setUsername(getSmtpUser());
	    mailSender.setPassword(getSmtpPwd());

	    mailSender.setHost(getSmtpHost());
	    mailSender.setPort(Integer.valueOf(getSmtpPort()));
	     
	    mailSender.setUsername(getSmtpUser());
	    mailSender.setPassword(getSmtpPwd());
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", Boolean.valueOf(getSmtpAuth()));
	    props.put("mail.smtp.starttls.enable", Boolean.valueOf(getSmtpTls()));
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}

	@Override
	public String toString() {
		return "EmailService [smtpHost=" + smtpHost + ", smtpPort=" + smtpPort + ", smtpUser=" + smtpUser + ", smtpPwd="
				+ smtpPwd + ", smtpAuth=" + smtpAuth + ", smtpTls=" + smtpTls + ", mailtextfornewEta="
				+ mailtextfornewEta + ", mailtextforRedAlert=" + mailtextforRedAlert + "]";
	}
	public  SMTPTransport connectToSmtp(Session session,
            String host,
            int port,
            String userEmail,
            boolean debug) throws Exception {

                         final URLName unusedUrlName = null;
                         SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
                         // If the password is non-null, SMTP tries to do AUTH LOGIN.
                         final String emptyPassword = "";
                         transport.connect(getSmtpHost(),String.valueOf(getSmtpPort()), getSmtpUser());
                         return transport;
                         }
         
         
         public void sendMsg() {
                 try {
                	 	Properties props = new Properties();
					        props.put("mail.smtp.starttls.enable",getSmtpTls());
					        props.put("mail.smtp.starttls.required",getSmtpTls());
					        props.put("mail.smtp.sasl.enable", getSmtpTls());
					 
					      
					
					        Session session = Session.getInstance(props);
					        session.setDebug(true);
					
					        SMTPTransport smtpTransport = connectToSmtp(session, this.smtpHost, Integer.valueOf(this.smtpPort),
					                                            this.smtpUser, true);
					
					        Message message = new MimeMessage(session);
					        message.setSubject(getSubject());
					        message.setText(getMailtextfornewEta());
					
					
					        Address toAddress = new InternetAddress();
					        message.setRecipient(Message.RecipientType.TO, toAddress);
					
					        smtpTransport.sendMessage(message, message.getAllRecipients());
					        smtpTransport.close();
			    } catch (MessagingException e) {
			        System.out.println("Messaging Exception");
			        System.out.println("Error: " + e.getMessage());
			    } catch (Exception e) {
			        System.out.println("Messaging Exception");
			        System.out.println("Error: " + e.getMessage());
			    }
         }

         //below are for SMS configuration
		public String getSmsHost() {
			return smsHost;
		}

		public void setSmsHost(String smsHost) {
			this.smsHost = smsHost;
		}

		public String getSmsPort() {
			return smsPort;
		}

		public void setSmsPort(String smsPort) {
			this.smsPort = smsPort;
		}

		public String getSmsUserName() {
			return smsUserName;
		}

		public void setSmsUserName(String smsUserName) {
			this.smsUserName = smsUserName;
		}

		public String getSmsPassword() {
			return smsPassword;
		}

		public void setSmsPassword(String smsPassword) {
			this.smsPassword = smsPassword;
		}

		public String getSmsETAText() {
			return smsETAText;
		}

		public void setSmsETAText(String smsETAText) {
			this.smsETAText = smsETAText;
		}

		public String getSmsRedText() {
			return smsRedText;
		}

		public void setSmsRedText(String smsRedText) {
			this.smsRedText = smsRedText;
		}
		
		
		
		 public String getSmsDisable() {
			return smsDisable;
		}

		public void setSmsDisable(String smsDisable) {
			this.smsDisable = smsDisable;
		}

		public void sendETASMS(String phoneNumber) {
			if(smsDisable!=null && !"YES".equalsIgnoreCase(smsDisable))
             new SMSutility().sendSMS(smsHost, smsPort, smsUserName, smsPassword, phoneNumber, smsETAText);
		 }
		 public void sendREDalertSMS(String phoneNumber) {
			 if(smsDisable!=null && !"YES".equalsIgnoreCase(smsDisable))
             new SMSutility().sendSMS(smsHost, smsPort, smsUserName, smsPassword, phoneNumber, smsRedText);
		 }
		
	}
