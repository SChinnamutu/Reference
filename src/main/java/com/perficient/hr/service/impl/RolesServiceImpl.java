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

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.RolesDAO;
import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.Roles;
import com.perficient.hr.orm.PrftDbObjectManager;
import com.perficient.hr.service.RolesService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfUtils;

@Service("rolesService")
public class RolesServiceImpl extends PrftDbObjectManager<Roles> implements RolesService{

	protected Logger logger = LoggerFactory.getLogger(RolesServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
    RolesDAO rolesDAO;
    
	@Override
	public Object loadRoles() {
		LoggerUtil.infoLog(logger, "Load Roles List Service Started");
	    List<Roles> list = null;
	    Session session = null;
		try {
			session = sessionFactory.openSession();
			list = rolesDAO.loadRoles(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Roles List" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Roles List" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object loadRolesById(String roleId) {
		LoggerUtil.infoLog(logger, "Load Role By Id Service Started. roleId: " + roleId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return session.get(Roles.class, Long.parseLong(roleId));
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Role By Id : " + roleId , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Role By Id : " + roleId , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object addRoles(Roles role, String userId) {
		LoggerUtil.infoLog(logger, "Add Roles Service Started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			role.setRoleName(PerfUtils.capitalizeFully(role.getRoleName()));
			role.setDtCreated(new Date());
			role.setDtModified(new Date());
			role.setCreatedBy(employee.getPk());
			role.setModifiedBy(employee.getPk());
			if(exists(role, "roleName", role.getRoleName(), null)){
				throw new RecordExistsException();
			} else {
				rolesDAO.addRoles(role, session);
				tx.commit();
				return role;
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Role Name already exists: "+role.getRoleName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Role Name already exists: "+role.getRoleName(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to add role: "+role.getRoleName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to add role: "+role.getRoleName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object updateRoles(Roles role, String userId) {
		LoggerUtil.infoLog(logger, "Update Roles Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			role.setRoleName(PerfUtils.capitalizeFully(role.getRoleName()));
			if(exists(role, "roleName", role.getRoleName(), role.getPk())){
				throw new RecordExistsException();
			} else {
				updateRole(role, userId, session);
				tx.commit();
				return true;	
			}			
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Role Name already exists: "+role.getRoleName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Role Name already exists: "+role.getRoleName(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update role: "+role.getRoleName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update role: "+role.getRoleName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	private boolean updateRole(Roles role, String userId, Session session){
		role.setDtModified(new Date());
		role.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
		return rolesDAO.updateRoles(role, session);
	}
	
	@Override
	public Object deleteRoles(Roles role, String userId) {
		LoggerUtil.infoLog(logger, "Delete role Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			role.setActive(PerfHrConstants.INACTIVE);
			updateRole(role, userId, session);
			tx.commit();
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to delete role: "+role.getRoleName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to delete role: "+role.getRoleName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
}