package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

	@Entity
	@Table(name="wts_process_tab")
	@javax.persistence.Cacheable
	public class WtsProcessTab implements Serializable { 
		private static final long serialVersionUID = 1L;
		@Id
		//@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="process_id")
	    private int processId;  
		@Column(name="name")
	    private String name;
		@Column(name="sequence")
		private int sequence;
		@Column(name="comments")
		private String comments;
		@Column(name="weight")
		private int weight;
		@Column(name="last_update_time")
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp lastUpdateTime;
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name="expected_start_time")
		private Timestamp expectedStartTime;
		@Column(name="expected_end_time")
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp expectedEndTime;
		@Column(name="enable_flag")
		private int enableFlag;
		
		@OneToMany(fetch=FetchType.EAGER)
	    @JoinColumn(name = "process_id", nullable = false,insertable=false,updatable=false) 
		@JsonProperty("Applications")
		private Set<WtsAppTab> line = new HashSet<WtsAppTab>();
	
		@OneToMany(fetch = FetchType.EAGER, mappedBy = "processId")
		@JsonProperty("NewProcessETA")
	    private Set<WtsNewEtaTab> eta = new HashSet<WtsNewEtaTab>();
		
		public int getProcessId() {
			return processId;
		}
		public void setProcessId(int processId) {
			this.processId = processId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getSequence() {
			return sequence;
		}
		public void setSequence(int sequence) {
			this.sequence = sequence;
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
		public Timestamp getExpectedStartTime() {
			return expectedStartTime;
		}
		public void setExpectedStartTime(Timestamp expectedStartTime) {
			this.expectedStartTime = expectedStartTime;
		}
		public Timestamp getExpectedEndTime() {
			return expectedEndTime;
		}
		public void setExpectedEndTime(Timestamp expectedEndTime) {
			this.expectedEndTime = expectedEndTime;
		}
		public int getEnableFlag() {
			return enableFlag;
		}
		public void setEnableFlag(int enableFlag) {
			this.enableFlag = enableFlag;
		}	
		
	}
	

