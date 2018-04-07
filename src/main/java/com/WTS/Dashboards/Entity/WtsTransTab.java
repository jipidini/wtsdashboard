package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

	@Entity
	@Table(name="wts_trans_tab")
	public class WtsTransTab implements Serializable { 
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="transaction_id")
	    private int transactionId;  
		@Column(name="process_id",nullable = false)
	    private Integer processId;  
		@Column(name="parent_id",nullable = false)
	    private Integer parentId;  
		@Column(name="child_id",nullable = false)
	    private Integer childId;  
		@Column(name="application_id",nullable = false)
	    private Integer applicationId; 


		@Column(name="event_date")
	    private String eventDate;

		 @JsonFormat(timezone= "CET", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name="start_transaction")
		private Date startTransaction ;
		@Column(name="end_transaction")

		 @JsonFormat(timezone= "CET", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private  Date endTransaction ;
		@Column(name="status_id")
		private int statusId;
		
		 @Column(name="sendemailflag")
         private int sendemailflag;
		 
		 @Column(name="sendetaemailflag")
         private int sendetaemailflag;
		
		//@OneToMany(fetch = FetchType.EAGER, mappedBy = "WtsTransTab")
		
		/* @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
		    @JoinColumn(name = "applicationId", referencedColumnName = "applicationId")
		    private WtsNewEtaTab eta;*/

	   
		public int getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}
		
		public int getProcessId() {
			if(processId==null){
				return 0;
			}else
			return processId;
		}
		public void setProcessId(int processId) {
			if(processId==0){
				this.processId = null;
			}else
			this.processId = processId;
		}
		
		public int getApplicationId() {
			if(applicationId==null){
				return 0;
			}else
				return applicationId;
		}
		public void setApplicationId(int applicationId) {
			if(applicationId==0){
				this.applicationId = null;
			}else
			this.applicationId = applicationId;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
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
		
		public String getEventDate() {
			return eventDate;
		}
		public void setEventDate(String eventDate) {
			this.eventDate = eventDate;
		}
		public  Date getStartTransaction() {
			return startTransaction;
		}
		@Temporal(TemporalType.TIMESTAMP)
		public void setStartTransaction(Date startTransaction) {
			this.startTransaction = startTransaction;
		}
		@Temporal(TemporalType.TIMESTAMP)
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


	}

