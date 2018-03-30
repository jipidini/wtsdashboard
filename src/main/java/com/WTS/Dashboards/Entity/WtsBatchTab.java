package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="wts_batch_tab")
@javax.persistence.Cacheable
public class WtsBatchTab implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="batch_id")
	private int batchId;
	@Column(name="application_id")
	private int applicationId;
	@Column(name="name")
	private String name;
	@Column(name="UOT")
	private String uot;
	@Column(name="process_id")
	private int processId;
	@Column(name="sequence")
	private int sequence;
	@Column(name="trig_id")
	private int trigId;
	@Column(name="weight")
	private int weight;
	@Column(name="comments")
	private String comments;
	 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;
	@Column(name="enable_flag")
	private int enableFlag;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "WtsBatchTab")
	
    //private WtsAppTab app;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUot() {
		return uot;
	}
	public void setUot(String uot) {
		this.uot = uot;
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	
	
	
	
	
}
