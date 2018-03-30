package com.WTS.Dashboards.Entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.WTS.Dashboards.dao.WtsAppTabDao;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="wts_treatment_tab")

public class WtsTreatTab implements Serializable { 
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="treatment_date")
	 @JsonFormat(timezone= "IST", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date treatmentDate;
	
	public Date getTreatmentDate() {
		return treatmentDate;
	}
	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	/*private static WtsTreatTab obj;
	
	private WtsTreatTab(){
		
	}
	
	public static WtsTreatTab getInstance(){
        if(obj == null){
            obj = new WtsTreatTab();
        }
        return obj;
    }
	*/
	/*public void putDate(){
		
	}*/
	
}
