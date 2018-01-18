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

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeView;
import com.perficient.hr.model.Notification;
import com.perficient.hr.service.EmployeeService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfUtils;

@Repository("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

	protected Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	private String getError = "Unable to get employee: ";
	private String updateError = "Unable to update employee: ";

    @Autowired
	private EmployeeDAO employeeDAO;
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
	
    @Override
    public Object loadByUserId(String pk) {
    	LoggerUtil.infoLog(logger, "Loading employee record for: "+pk);
		Session session = null;
		EmployeeView employee = null;
		try {
			session = sessionFactory.openSession();
			employee = employeeDAO.loadByUserId(pk, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, getError+pk , e);
			return ExceptionHandlingUtil.returnErrorObject(getError+pk , e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employee;
	}

    @Override
	public Object loadById(String pk) {
		LoggerUtil.infoLog(logger, "Loading employee record for: "+pk);
		Session session = null;
		Employee employee = null;
		try {
			session = sessionFactory.openSession();
			employee = employeeDAO.loadById(pk, session);;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, getError+pk , e);
			return ExceptionHandlingUtil.returnErrorObject(getError+pk , e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employee;
	}
    
    @Override
	public Object loadEmployees() {
    	LoggerUtil.infoLog(logger, "Load All employee list.");
		Session session = null;
		List<EmployeeView> list = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadEmployees(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object updateEmployee(Employee employee, String userId) {
		LoggerUtil.infoLog(logger, "Update employee details for the Employee: "+userId);
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			employee.setFirstName(PerfUtils.capitalizeFully(employee.getFirstName()));
			employee.setLastName(PerfUtils.capitalizeFully(employee.getLastName()));
			employee.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
			employee.setDtModified(new Date());
			employeeDAO.updateEmployee(employee, userId, session);
			tx.commit();
		} catch(Exception e){
			LoggerUtil.errorLog(logger, updateError+employee.getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(updateError+employee.getEmployeeId(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
		return true;
	}

	@Override
	public Object addEmployee(Employee employee, String userId) {
		LoggerUtil.infoLog(logger, "Save New Employee deatils");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee loggedEmployee = employeeDAO.loadById(userId, session);
			employee.setFirstName(PerfUtils.capitalizeFully(employee.getFirstName()));
			employee.setLastName(PerfUtils.capitalizeFully(employee.getLastName()));
			employee.setDtCreated(new Date());
			employee.setDtModified(new Date());
			employee.setCreatedBy(loggedEmployee.getPk());
			employee.setModifiedBy(loggedEmployee.getPk());
			session.save(employee);
			tx.commit();
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to save employee: "+employee.getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to save employee: "+employee.getEmployeeId(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);	
		}
		return employee;
	}

	@Override
	public Object loadEmployeeByDesHistory(String stDate,
			String endDate, String designationName) {
		LoggerUtil.infoLog(logger, "Load Employee Designation History");
		List<EmployeeView> list = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadEmployeeByDesHistory(stDate, endDate, designationName, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to employee List By Designation", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to employee List By Designation", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object loadAllEmployees() {
		LoggerUtil.infoLog(logger, "Load All employee list.");
		Session session = null;
		List<Employee> list = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadAllEmployees(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load all employee List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load all employee List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object updateNotification(Notification notification) {
		LoggerUtil.infoLog(logger, "Update notification details ");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			notification.setFlag(PerfHrConstants.SUBMITTED);
			employeeDAO.updateNotification(notification,session);
			tx.commit();
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Error ocured while update notification status", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Error ocured while update notification status", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
		return null;
	}
}
