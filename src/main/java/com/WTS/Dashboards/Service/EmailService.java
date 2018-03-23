package com.WTS.Dashboards.Service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Value("${spring.mail.host}")
	private String smtpHost;
	
	@Value("${spring.mail.port}")
	private String smtpPort;
	
	@Value("${spring.mail.username}")
	private String smtpUser;
	
	@Value("${spring.mail.password}")
	private String smtpPwd;
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String smtpAuth;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String smtpTls;
	public String getSmtpHost() {
		return smtpHost;
	}

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
	
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//	    mailSender.setHost(getSmtpHost());
//	    mailSender.setPort(Integer.valueOf(getSmtpPort()));
//	     
//	    mailSender.setUsername(getSmtpUser());
//	    mailSender.setPassword(getSmtpPwd());
	     
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
}