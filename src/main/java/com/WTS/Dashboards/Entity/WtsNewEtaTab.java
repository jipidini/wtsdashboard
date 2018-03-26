package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wts_new_eta_tab")
@javax.persistence.Cacheable
public class WtsNewEtaTab implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="eta_id")
	private int etaId;
	@Column(name="event_date")
	private String eventDate;
	@Column(name="process_id")
	private int processId;
	@Column(name="batch_id")
	private int batchId;
	@Column(name="application_id")
	private int applicationId;
	@Column(name="new_eta_start_transaction")
	private Timestamp newEtaStartTransaction;
	@Column(name="new_eta_end_transaction")
	private Timestamp newEtaEndTransaction;
	@Column(name="each_problem_flag")
	private int problemFlag;
	
	public int getEtaId() {
		return etaId;
	}
	public void setEtaId(int etaId) {
		this.etaId = etaId;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	
	public Timestamp getNewEtaStartTransaction() {
		return newEtaStartTransaction;
	}
	public void setNewEtaStartTransaction(Timestamp newEtaStartTransaction) {
		this.newEtaStartTransaction = newEtaStartTransaction;
	}
	public Timestamp getNewEtaEndTransaction() {
		return newEtaEndTransaction;
	}
	public void setNewEtaEndTransaction(Timestamp newEtaEndTransaction) {
		this.newEtaEndTransaction = newEtaEndTransaction;
	}
	public int getProblemFlag() {
		return problemFlag;
	}
	public void setProblemFlag(int problemFlag) {
		this.problemFlag = problemFlag;
	}
	
	
}
