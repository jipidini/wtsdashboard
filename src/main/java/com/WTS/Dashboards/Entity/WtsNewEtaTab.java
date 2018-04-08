package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@Column(name="parent_id",nullable = false)
    private Integer parentId;  
	@Column(name="child_id",nullable = false)
    private Integer childId;
	@Column(name="application_id")
	private int applicationId;
	@Column(name="new_eta_start_transaction")
	 @JsonFormat(timezone= "CET", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp newEtaStartTransaction;
	@Column(name="new_eta_end_transaction")
	 @JsonFormat(timezone= "CET", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
	
	public Integer getParentId() {
		if(parentId==null){
			return 0;
		}else
		return parentId;
	}
	public void setParentId(Integer parentId) {
		if(parentId==0){
			this.parentId = null;
		}else
		this.parentId = parentId;
	}
	public Integer getChildId() {
		if(childId==null){
			return 0;
		}else
		return childId;
	}
	
	public void setChildId(Integer childId) {
		if(childId==0){
			this.childId = null;
		}else
		this.childId = childId;
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
