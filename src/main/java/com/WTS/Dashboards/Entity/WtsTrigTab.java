package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


	@Entity
	@Table(name="wts_trig_tab")
	@javax.persistence.Cacheable
	public class WtsTrigTab implements Serializable { 
		private static final long serialVersionUID = 1L;
		@Id
		//@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="trig_id")
	    private int trigId;  
		@Column(name="expected_start_time")
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp expectedStartTime;
		@Column(name="expected_end_time")
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp expectedEndTime;
		@Column(name="host")
		private String host;
		@Column(name="port")
		private String port;
		@Column(name="trigger_file")
		private String triggerFile;
		@Column(name="trigger_file_path")
		private String triggerFilePath;
		@Column(name="last_update_time")
		 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private Timestamp lastUpdateTime;
		@Column(name="comments")
		private String comments;
		public int getTrigId() {
			return trigId;
		}
		public void setTrigId(int trigId) {
			this.trigId = trigId;
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
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
		public String getTriggerFile() {
			return triggerFile;
		}
		public void setTriggerFile(String triggerFile) {
			this.triggerFile = triggerFile;
		}
		public String getTriggerFilePath() {
			return triggerFilePath;
		}
		public void setTriggerFilePath(String triggerFilePath) {
			this.triggerFilePath = triggerFilePath;
		}
		public Timestamp getLastUpdateTime() {
			return lastUpdateTime;
		}
		public void setLastUpdateTime(Timestamp lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		
		
		
}

