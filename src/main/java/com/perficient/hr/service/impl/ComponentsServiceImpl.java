package com.perficient.hr.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.ComponentsDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.Components;
import com.perficient.hr.model.Employee;
import com.perficient.hr.service.ComponentsService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("componentsService")
public class ComponentsServiceImpl implements ComponentsService {

protected Logger logger = LoggerFactory.getLogger(ComponentsServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
    ComponentsDAO componentsDAO;
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
	
	@Override
	public Object loadComponents() {
		LoggerUtil.infoLog(logger, "Load Components List Service Started");
	    List<Components> list = null;
	    Session session = null;
		try {
			session = sessionFactory.openSession();
			list = componentsDAO.loadComponents(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Components List" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Components List" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object loadComponentById(String componentId) {
		LoggerUtil.infoLog(logger, "Load Component By Id Service Started. componentId: " + componentId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return session.get(Components.class, Long.parseLong(componentId));
		}catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Component By Id : " + componentId , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Component By Id : " + componentId , e);
		}
		finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object loadComponentByName(String componentName) {
		LoggerUtil.infoLog(logger, "Load Component By Name Service Started. componentName: " + componentName);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return componentsDAO.loadComponentByName(componentName, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Component By componentName: " + componentName, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Component By componentName: " + componentName, e);
		}
		finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object addComponent(Components component, String userId) {
		LoggerUtil.infoLog(logger, "Add Component Service Started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			component.setDtCreated(new Date());
			component.setDtModified(new Date());
			component.setCreatedBy(employee.getPk());
			component.setModifiedBy(employee.getPk());
			componentsDAO.addComponent(component, session);
			tx.commit();
			return component;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to add component: "+component.getComponentName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to add component: "+component.getComponentName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
	
	private boolean updateComponent(Components component, String userId, Session session){
		component.setDtModified(new Date());
		component.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
		return componentsDAO.updateComponent(component, session);
	} 
	

	@Override
	public Object updateComponent(Components component, String userId) {
		LoggerUtil.infoLog(logger, "Update Component Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			updateComponent(component, userId, session);
			tx.commit();
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update component: "+component.getComponentName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update component: "+component.getComponentName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object deleteComponent(Components component, String userId) {
		LoggerUtil.infoLog(logger, "Delete Component Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			component.setActive(PerfHrConstants.INACTIVE);
			updateComponent(component, userId, session);
			tx.commit();
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Delete component: "+component.getComponentName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Delete component: "+component.getComponentName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
}