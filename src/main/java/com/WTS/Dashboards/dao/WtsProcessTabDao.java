package com.WTS.Dashboards.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.WTS.Dashboards.DTO.Application;
import com.WTS.Dashboards.DTO.ApplicationMappingDTO;
import com.WTS.Dashboards.DTO.ProcessDTO;
import com.WTS.Dashboards.Entity.WtsAppMappingTab;
import com.WTS.Dashboards.Entity.WtsAppTab;
import com.WTS.Dashboards.Entity.WtsNewEtaTab;
import com.WTS.Dashboards.Entity.WtsProcessAppMapTab;
import com.WTS.Dashboards.Entity.WtsProcessTab;
import com.WTS.Dashboards.Utility.TreatmentDate;
import com.fasterxml.jackson.annotation.JsonProperty;

@Transactional
@Repository
public class WtsProcessTabDao implements IWtsDaoInterface {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Autowired
	private WtsAppTabDao appDao;
	
	@Autowired
	private WtsProcessAppMapTabDao proAppMapDao;
	
	@Autowired
	private WtsAppMappingTabDao appMapDao;
	
	public WtsProcessTab getProcessById(int processId) {
		return entityManager.find(WtsProcessTab.class, processId);
	}
	@SuppressWarnings("unchecked")
	
	public List<WtsProcessTab> getAllprocess() {
		String hql = "FROM WtsProcessTab as pros ORDER BY pros.processId";
		return (List<WtsProcessTab>) entityManager.createQuery(hql).getResultList();
	}

	public Timestamp getProcessStartTime(int processId) {
		   String hql="FROM WtsProcessTab as app WHERE app.processId=:proc";
		   
		 List <WtsProcessTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getExpectedStartTime();
		  else
	   return null;
	}
	
	public Timestamp getProcessEndTime(int processId) {
		   String hql="FROM WtsProcessTab as app WHERE app.processId=:proc";
		   
		 List <WtsProcessTab> ls=entityManager.createQuery(hql).setParameter("proc",processId).getResultList();
		  if(ls!=null && !ls.isEmpty() && ls.get(0)!=null)
			  return ls.get(0).getExpectedEndTime();
		  else
	   return null;
	}
	
	
	
	public List<ApplicationMappingDTO> getAllDTOsForApplication(int applicationId, int processId, int parentId) {
		List<ApplicationMappingDTO> processDTOs= new ArrayList<ApplicationMappingDTO>();
		ApplicationMappingDTO DTO= new ApplicationMappingDTO();
		WtsAppTab parentApp=appDao.getAppById(applicationId);
		if(parentApp!=null) {
			DTO.setParentId(parentApp.getApplicationId());
			DTO.setLastUpdateTime(parentApp.getLastUpdateTime());
			DTO.setName(parentApp.getName());
			DTO.setEnableFlag(parentApp.getEnableFlag());
			DTO.setComments(parentApp.getComments());
			DTO.setEta(this.getETABatchTxn(processId, TreatmentDate.getInstance().getTreatmentDate(), parentId));
			if(parentId>0){
				WtsAppMappingTab proMap=appMapDao.getAppMappingsByParent(parentId, applicationId,processId);
				if(proMap!=null) {
					DTO.setExpectedStartTime(proMap.getStartTime());
					DTO.setExpectedEndTime(proMap.getEndTime());
				}
			}else if(processId>0) {
				WtsProcessAppMapTab proMap=proAppMapDao.getAllAppMappingsByProcess(processId, applicationId);
				if(proMap!=null) {
					DTO.setExpectedStartTime(proMap.getStartTime());
					DTO.setExpectedEndTime(proMap.getEndTime());
				}
			}
			
		}
		Set<Application> childrens=new HashSet<>();
		String hql = "FROM WtsAppMappingTab as mapp WHERE mapp.parentId=:app AND mapp.processId=:proc";
		List<WtsAppMappingTab> mappingDBs=entityManager.createQuery(hql).setParameter("app",applicationId).setParameter("proc",processId).getResultList();
		if(mappingDBs!=null && !mappingDBs.isEmpty())
		{
			Iterator<WtsAppMappingTab> procDBItr=mappingDBs.iterator();
			while (procDBItr.hasNext()) {
				WtsAppMappingTab mapping = (WtsAppMappingTab) procDBItr.next();
				childrens.add(appDao.convertApplicationDTO(parentApp.getApplicationId(),appDao.getAppById(mapping.getChildId()),processId));
			}
		}
		DTO.setApplications(childrens);
		processDTOs.add(DTO);
			return processDTOs;
	}
	
	public List<ProcessDTO> getAllprocessDTOs() {
		String hql = "FROM WtsProcessTab as pros ORDER BY pros.processId";
		List<WtsProcessTab> processDBs=entityManager.createQuery(hql).getResultList();
		if(processDBs!=null && !processDBs.isEmpty())
		{
			List<ProcessDTO> processDTOs= new ArrayList<ProcessDTO>();
			Iterator<WtsProcessTab> procDBItr=processDBs.iterator();
			while (procDBItr.hasNext()) {
				WtsProcessTab wtsProcessTab = (WtsProcessTab) procDBItr.next();
				processDTOs.add(this.convertToProcessDTO(wtsProcessTab));
			}
			
			return processDTOs;
		}else
		return null;
	}
	
	public void addProcess(WtsProcessTab process) {
		entityManager.persist(process);
		
	}

	public ProcessDTO convertToProcessDTO(WtsProcessTab processDB) {
		ProcessDTO dto=null;
		if(processDB!=null) {
			dto= new ProcessDTO();
			dto.setProcessId(processDB.getProcessId());
			dto.setName(processDB.getName());
			dto.setSequence(processDB.getSequence());
			dto.setComments(processDB.getComments());
			dto.setWeight(processDB.getWeight());
			dto.setLastUpdateTime(processDB.getLastUpdateTime());
			dto.setExpectedStartTime(processDB.getExpectedStartTime());
			dto.setExpectedEndTime(processDB.getExpectedEndTime());
			dto.setEnableFlag(processDB.getEnableFlag());
			dto.setEta(processDB.getEta());
			dto.setEta(processDB.getEta());
			dto.setApplications(convertApplicationDTO(appDao.getAllAppsByProcess(processDB.getProcessId()),processDB.getProcessId()));
			 
		}
		return dto;
	}
	
	public Set<Application> convertApplicationDTO(List<WtsAppTab> allAppsByProcess,int processId) {
		if(allAppsByProcess!=null && !allAppsByProcess.isEmpty()) {
			Set<Application> appDtos=new HashSet<Application>();
			Iterator<WtsAppTab> appDBItr=allAppsByProcess.iterator();
			while (appDBItr.hasNext()) {
				WtsAppTab wtsAppTab = (WtsAppTab) appDBItr.next();
				appDtos.add(appDao.convertApplicationDTOforProcess(wtsAppTab, processId));
			}
			
			return appDtos;
		}
		return null;
	}
	public void updateProcess(WtsProcessTab process) {
		WtsProcessTab pros = getProcessById(process.getProcessId());
		pros.setComments(process.getComments());
		pros.setWeight(process.getWeight());
		entityManager.flush();
		
		
	}

	
	public void deleteProcess(int processId) {
		entityManager.remove(getProcessById(processId));
		
	}


	
	public boolean processExists(String name) {
		String hql = "Select pros.* FROM WtsProcessTab as pros WHERE pros.name = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name).getResultList().size();
		return count > 0 ? true : false;
	}
	
	public Set<WtsNewEtaTab> getETABatchTxn(int processId, String trtDt,int parentId) {
		String hql = "from WtsNewEtaTab WHERE processId=? and eventDate= ? AND parent_id= ? and application_id IS null and childId IS NOT NULL";

		Query qry = entityManager.createQuery(hql);
		qry.setParameter(1, processId);
		qry.setParameter(2, trtDt);
		qry.setParameter(3, parentId);
		if (qry.getResultList() != null && !qry.getResultList().isEmpty())
			return (Set<WtsNewEtaTab>) qry.getResultList().get(0);
		else
			return null;

	}
	
	
}
