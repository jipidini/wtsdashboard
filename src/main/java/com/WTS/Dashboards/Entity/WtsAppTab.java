package com.WTS.Dashboards.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
               
                @Column(name="comments")
                private String comments;
              
                @Column(name="last_update_time")
                @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                private Timestamp lastUpdateTime;
                @Column(name="enable_flag")
                
                private int enableFlag;
               
                
               
                @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentId")
                @JsonProperty("appMappings")
    private Set<WtsAppMappingTab> appMappings = new HashSet<WtsAppMappingTab>();
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
