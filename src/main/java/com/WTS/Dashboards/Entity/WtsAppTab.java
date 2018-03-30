package com.WTS.Dashboards.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name="wts_app_tab")
@javax.persistence.Cacheable
public class WtsAppTab implements Serializable { 
                private static final long serialVersionUID = 1L;
                @Id
                //@GeneratedValue(strategy=GenerationType.AUTO)
                @Column(name="application_id")
    private int applicationId;  
                @Column(name="name")
    private String name;
                @Column(name="process_id")                
                private int processId;
                @Column(name="sequence")
                private int sequence;
                @Column(name="trig_id")
                private int trigId;
                @Column(name="comments")
                private String comments;
                @Column(name="weight")
                private int weight;
                @Column(name="transaction_id")
                private WtsTransTab transactionId;
                @Column(name="last_update_time")
                @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                private Timestamp lastUpdateTime;
                @Column(name="enable_flag")
                
                private int enableFlag;
                @Column(name="buffer_minute_time")
                private int bufferTime;
                @Column(name="email_id")
                private String emailId;
               
                @Column(name="support_contact")
                private String supportContact;
                
                
                @Column(name="start_time")
                @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                private Timestamp startTime;
                
                @Column(name="end_time")
                @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                private Timestamp endTime;
                
                @ManyToOne(fetch=FetchType.EAGER)
                @JoinColumn(name = "process_id", nullable = false,insertable=false,updatable=false) 
    private WtsProcessTab pro;
                @OneToMany(fetch = FetchType.EAGER, mappedBy = "batchId")
                @JsonProperty("Batches")
    private Set<WtsBatchTab> bat = new HashSet<WtsBatchTab>();
                @OneToMany(fetch = FetchType.EAGER, mappedBy = "applicationId")
                @JsonProperty("Transactions")
    private Set<WtsTransTab> tran = new HashSet<WtsTransTab>();
                
                @OneToMany(fetch = FetchType.EAGER, mappedBy = "applicationId")
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
                public WtsTransTab getTransactionId() {
                                return transactionId;
                }
                public void setTransactionId(WtsTransTab transactionId) {
                                this.transactionId = transactionId;
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
                
                
}
