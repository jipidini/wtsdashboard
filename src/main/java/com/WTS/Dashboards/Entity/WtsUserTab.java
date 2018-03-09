package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


	@Entity
	@Table(name="wts_user_tab")
	public class WtsUserTab implements Serializable { 
		private static final long serialVersionUID = 1L;
		@Id
		//@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="user_id")
	    private int userId;  
		@Column(name="application_id")
	    private int appliactionId; 
		@Column(name="name")
		private String name;
		@Column(name="comments")
		private String comments;
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getAppliactionId() {
			return appliactionId;
		}
		public void setAppliactionId(int appliactionId) {
			this.appliactionId = appliactionId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		
		
}

