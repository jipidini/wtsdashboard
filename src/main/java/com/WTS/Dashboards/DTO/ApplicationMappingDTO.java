package com.WTS.Dashboards.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationMappingDTO implements Serializable {
	 private int parentId;  
		
	    private String name;
		
		private int sequence;
		
		private String comments;
		
		private int weight;
		
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp lastUpdateTime;
		
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp expectedStartTime;
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp expectedEndTime;
	
		private int enableFlag;
		
		
	    private Set<WtsNewEtaTab> eta = new HashSet<WtsNewEtaTab>();
	    @JsonProperty("Applications")
		 private Set<Application> applications = new HashSet<Application>();
		
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
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
		public Set<WtsNewEtaTab> getEta() {
			return eta;
		}
		public void setEta(Set<WtsNewEtaTab> eta) {
			this.eta = eta;
		}
		public Set<Application> getApplications() {
			return applications;
		}
		public void setApplications(Set<Application> applications) {
			this.applications = applications;
		}
	    
}
