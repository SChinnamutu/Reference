package com.perficient.hr.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.ProjectMembersDAO;
import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.ProjectMembers;
import com.perficient.hr.orm.PrftDbObjectManager;
import com.perficient.hr.service.ProjectMembersService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("projectMembersService")
public class ProjectMembersServiceImpl extends PrftDbObjectManager<ProjectMembers> implements ProjectMembersService{

protected Logger logger = LoggerFactory.getLogger(ProjectMembersServiceImpl.class);

	@Autowired
	EmployeeDAO employeeDAO;

	@Autowired
	ProjectMembersDAO projectMembersDAO; 

	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
    
	@Override
	public Object loadAllProjectMembers() {
    	LoggerUtil.infoLog(logger, "Service to Load All Project Members");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return projectMembersDAO.loadAllProjectMembers(session);
		}catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load All Project Members" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load All Project Members" , e);
		}
		finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
    
	@Override
	public Object loadProjectMembersByProjectId(String projectPk) {
    	LoggerUtil.infoLog(logger, "Service to Load All Project Members of Project: " + projectPk);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return projectMembersDAO.loadProjectMembersByProjectId(projectPk, session);
		}catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load All Project Members of Project: " + projectPk , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load All Project Members of Project: " + projectPk, e);
		}
		finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object saveProjectMember(ProjectMembers projectMembers, String userId) {
		LoggerUtil.infoLog(logger, "Service to Save Project member details: "+projectMembers.getEmployeeId());
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			projectMembers.setDtCreated(new Date());
			projectMembers.setDtModified(new Date());
			projectMembers.setCreatedBy(employee.getPk());
			projectMembers.setModifiedBy(employee.getPk());
			if(validateMember(projectMembers)){
				projectMembersDAO.updateProjectMember(projectMembers, session);
				tx.commit();
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Project Member already exists within time range: "+projectMembers.getProjectId().getProjectName()+"/"
					+projectMembers.getEmployeeId().getFirstName()+"-"+projectMembers.getEmployeeId().getLastName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Project Member already exists within time range: "+projectMembers.getProjectId().getProjectName()+"/"
					+projectMembers.getEmployeeId().getFirstName()+"-"+projectMembers.getEmployeeId().getLastName(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Save Project member deatils: "+projectMembers.getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Save Project member deatils: "+projectMembers.getEmployeeId(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
		return true;
	}

	@Override
	public Object loadProjectMemberById(String projectMemberId) {
		LoggerUtil.infoLog(logger, "Service to Load Project Members By projectMemberId : " + projectMemberId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return projectMembersDAO.loadProjectMemberById(projectMemberId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Project Members By projectMemberId : " + projectMemberId , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Project Members By projectMemberId : " + projectMemberId , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object updateProjectMember(ProjectMembers projectMembers, String userId) {
		LoggerUtil.infoLog(logger, "Service to update Project member deatils : " + projectMembers.getEmployeeId());
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			projectMembers.setDtModified(new Date());
			projectMembers.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
			if(validateMember(projectMembers)){
				projectMembersDAO.updateProjectMember(projectMembers, session);
				tx.commit();
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Project Member already exists within time range: "+projectMembers.getProjectId().getProjectName()+"/"
					+projectMembers.getEmployeeId().getFirstName()+"-"+projectMembers.getEmployeeId().getLastName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update Project member deatils : " + projectMembers.getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update Project member deatils : " + projectMembers.getEmployeeId(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private boolean validateMember(ProjectMembers projectMembers) throws RecordExistsException{
		if((projectMembers.getDtStarted().getTime() < projectMembers.getProjectId().getStartDate().getTime() 
				|| projectMembers.getDtStarted().getTime() > projectMembers.getProjectId().getEndDate().getTime()) 
				|| (projectMembers.getDtEnded().getTime() < projectMembers.getProjectId().getStartDate().getTime() 
				|| projectMembers.getDtEnded().getTime() > projectMembers.getProjectId().getEndDate().getTime())){
			throw new RecordExistsException("Project Member Start/End date is not within the Project Date Range.");
		}
		HashMap<Object, Object> map = new HashMap<>();
		map.put("projectPk", projectMembers.getProjectId().getPk());
		map.put("employeeId", projectMembers.getEmployeeId().getPk());
		map.put("active", PerfHrConstants.ACTIVE);
		String sql = " from ProjectMembers as pm where pm.projectId.pk=:projectPk and pm.employeeId.pk=:employeeId and pm.active=:active";
		if(projectMembers.getPk() != null){
			sql += " and pm.pk <> :pk";
			map.put("pk", projectMembers.getPk());
		}
		List<ProjectMembers> projectMembersList = executeQuery(sql, map);
		if(projectMembersList.size() > 0){
			for(ProjectMembers projMember: projectMembersList){
				if((projectMembers.getDtStarted().getTime() >= projMember.getDtStarted().getTime()
						&& projectMembers.getDtStarted().getTime() <= projMember.getDtEnded().getTime())
						||(projectMembers.getDtEnded().getTime() >= projMember.getDtStarted().getTime()
								&& projectMembers.getDtEnded().getTime() <= projMember.getDtEnded().getTime())
						||(projectMembers.getDtStarted().getTime() <= projMember.getDtStarted().getTime()
								&& projectMembers.getDtEnded().getTime() >= projMember.getDtEnded().getTime())){
					throw new RecordExistsException("Project Member already exists within time range: "+projectMembers.getProjectId().getProjectName()+"/"
							+projectMembers.getEmployeeId().getFirstName()+"-"+projectMembers.getEmployeeId().getLastName());
				}
			}				
		}
		return true;
	}
	
	@Override
	public Object deleteProjectMember(ProjectMembers projectMembers,
			String userId) {
		LoggerUtil.infoLog(logger, "Service to delete Project member deatils : " + projectMembers.getEmployeeId());
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			projectMembers.setDtModified(new Date());
			projectMembers.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
			projectMembers.setActive(PerfHrConstants.INACTIVE);
			projectMembersDAO.updateProjectMember(projectMembers, session);
			tx.commit();
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to delete Project member deatils : " + projectMembers.getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to delete Project member deatils : " + projectMembers.getEmployeeId(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
		return true;
	}
}
