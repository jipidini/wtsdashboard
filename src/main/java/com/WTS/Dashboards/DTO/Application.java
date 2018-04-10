package com.WTS.Dashboards.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Entity.WtsTransTab;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Application implements Serializable {
private int applicationId;
	private String name;
	 private int processId;
	 private int sequence;
	 private int trigId;
	 private String comments;
	 private int weight;
	 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	 private Timestamp lastUpdateTime;
	 private int enableFlag;
	 private int bufferTime;
	 private String emailId;
	 private String supportContact;
	 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	 private Timestamp startTime;
	 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	 private Timestamp endTime;
	 	@JsonProperty("Transactions")
	    private Set<WtsTransTab> tran = new HashSet<WtsTransTab>();
        
        @JsonProperty("NewETA")
        private Set<WtsNewEtaTab> eta = new HashSet<WtsNewEtaTab>();
        
        
	
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getTrigId() {
		return trigId;
	}
	public void setTrigId(int trigId) {
		this.trigId = trigId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}
	public int getBufferTime() {
		return bufferTime;
	}
	public void setBufferTime(int bufferTime) {
		this.bufferTime = bufferTime;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getSupportContact() {
		return supportContact;
	}
	public void setSupportContact(String supportContact) {
		this.supportContact = supportContact;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Set<WtsTransTab> getTran() {
		return tran;
	}
	public void setTran(Set<WtsTransTab> tran) {
		this.tran = tran;
	}
	public Set<WtsNewEtaTab> getEta() {
		return eta;
	}
	public void setEta(Set<WtsNewEtaTab> eta) {
		this.eta = eta;
	}
	 
}
