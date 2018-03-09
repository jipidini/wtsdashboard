package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

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
		@Column(name="batch_id",nullable = false)
	    private Integer batchId;  
		@Column(name="application_id",nullable = false)
	    private Integer applicationId; 


		@Column(name="event_date")
	    private String eventDate;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		@Column(name="start_transaction")
		private Date startTransaction ;
		@Column(name="end_transaction")

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
		private  Date endTransaction ;
		@Column(name="status_id")
		private int statusId;
		
		
		@OneToMany(fetch = FetchType.EAGER, mappedBy = "WtsTransTab")
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
		public int getBatchId() {
			if(batchId==null){
				return 0;
			}else
			return batchId;
		}
		public void setBatchId(int batchId) {
			if(batchId==0){
				this.batchId = null;
			}else
			this.batchId = batchId;
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
		
		
	}

