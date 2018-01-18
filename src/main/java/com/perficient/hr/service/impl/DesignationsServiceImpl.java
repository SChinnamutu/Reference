package com.perficient.hr.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.perficient.hr.dao.DesignationsDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.Designations;
import com.perficient.hr.model.Employee;
import com.perficient.hr.orm.PrftDbObjectManager;
import com.perficient.hr.service.DesignationsService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfUtils;

@Service("designationsService")
public class DesignationsServiceImpl extends PrftDbObjectManager<Designations> implements DesignationsService {

	protected Logger logger = LoggerFactory.getLogger(DesignationsServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
    DesignationsDAO designationsDAO;
	
    @Override
	public Object loadDesignations() {
    	LoggerUtil.infoLog(logger, "Load Job Title List Service Started");
	    List<Designations> list = null;
	    Session session = null;
		try {
			session = sessionFactory.openSession();
			list = designationsDAO.loadDesignations(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Job Title List" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Job Title List" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object addDesignation(Designations designation, String userId) {
		LoggerUtil.infoLog(logger, "Add Designation Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			designation.setDesignation(PerfUtils.capitalizeFully(designation.getDesignation()));
			designation.setDtCreated(new Date());
			designation.setDtModified(new Date());
			designation.setCreatedBy(employee.getPk());
			designation.setModifiedBy(employee.getPk());
			if(exists(designation, "designation", designation.getDesignation(), null)){
				throw new RecordExistsException();
			} else {
				designationsDAO.addDesignation(designation, session);
				tx.commit();
				return designation;	
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Job Title already exists: "+designation.getDesignation(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Job Title already exists: "+designation.getDesignation(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to add Job Title: "+designation.getDesignation(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to add Job Title: "+designation.getDesignation(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object loadDesignationById(String designationId) {
		LoggerUtil.infoLog(logger, "Load Job Title By Id Service Started. designationId: " + designationId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return session.get(Designations.class, Long.parseLong(designationId));
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Job Title By Id : " + designationId , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Job Title By Id : " + designationId , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object updateDesignation(Designations designation, String userId) {
		LoggerUtil.infoLog(logger, "Update Job Title Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			designation.setDesignation(PerfUtils.capitalizeFully(designation.getDesignation()));
			if(exists(designation, "designation", designation.getDesignation(), designation.getPk())){
				throw new RecordExistsException();
			} else {
				updateDesignation(designation, userId, session);
				tx.commit();
				return true;	
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Unable to Update Job Title: "+designation.getDesignation(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Job Title already exists: "+designation.getDesignation(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update Job Title: "+designation.getDesignation(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update Job Title: "+designation.getDesignation(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
	
	private boolean updateDesignation(Designations designation, String userId, Session session){
		designation.setDtModified(new Date());
		designation.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
		return designationsDAO.updateDesignation(designation, session);
	} 
	
	@Override
	public Object deleteDesignation(Designations designation, String userId) {
		LoggerUtil.infoLog(logger, "Delete Designation Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			if(exists(Employee.class, "designations", designation, null)){
				throw new RecordExistsException();
			} else {
				designation.setActive(PerfHrConstants.INACTIVE);
				updateDesignation(designation, userId, session);
				tx.commit();
				return true;
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Unable to Delete Job Title: "+designation.getDesignation(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Delete Job Title as its still assigned to Employees: "+designation.getDesignation(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Delete designation: "+designation.getDesignation(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Delete Job Title: "+designation.getDesignation(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object loadDesignationByName(String designationName) {
		LoggerUtil.infoLog(logger, "Load Job Title By Name Service Started. designationName: " + designationName);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return designationsDAO.loadDesignationByName(designationName, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Designation By designationName: " + designationName, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Designation By designationName: " + designationName, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
}