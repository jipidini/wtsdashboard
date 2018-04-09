package com.WTS.Dashboards.DTO;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransactionDTO implements Serializable {
	private int transactionId;

	private Integer processId;

	private Integer parentId;

	private Integer childId;

	private Integer applicationId;

	private String eventDate;
	
	//for process health check button code
	private Integer processStatus;
	//for app health check button code
	private Integer appButtonStatus;
	
	@JsonFormat(timezone = "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")

	private Date startTransaction;

	@JsonFormat(timezone = "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTransaction;

	private int statusId;

	private int sendemailflag;

	private int sendetaemailflag;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public Date getStartTransaction() {
		return startTransaction;
	}

	public void setStartTransaction(Date startTransaction) {
		this.startTransaction = startTransaction;
	}

	public Date getEndTransaction() {
		return endTransaction;
	}

	public void setEndTransaction(Date endTransaction) {
		this.endTransaction = endTransaction;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getSendemailflag() {
		return sendemailflag;
	}

	public void setSendemailflag(int sendemailflag) {
		this.sendemailflag = sendemailflag;
	}

	public int getSendetaemailflag() {
		return sendetaemailflag;
	}

	public void setSendetaemailflag(int sendetaemailflag) {
		this.sendetaemailflag = sendetaemailflag;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public Integer getAppButtonStatus() {
		return appButtonStatus;
	}

	public void setAppButtonStatus(Integer appButtonStatus) {
		this.appButtonStatus = appButtonStatus;
	}

	
	
	

}
