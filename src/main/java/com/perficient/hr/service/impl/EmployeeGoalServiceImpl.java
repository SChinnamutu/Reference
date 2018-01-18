package com.perficient.hr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeGoalDAO;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeGoals;
import com.perficient.hr.model.ProjectGoals;
import com.perficient.hr.service.EmployeeGoalService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;

@Repository("EmployeeGoalServiceDAO")
public class EmployeeGoalServiceImpl implements EmployeeGoalService{

	protected Logger logger = LoggerFactory.getLogger(ComponentsServiceImpl.class);

	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }

    
	@Autowired
	EmployeeGoalDAO employeeGoalDAO;
	
	@Autowired
    EmployeeDAO employeeDAO;

	
	@Override
	public Object saveGoalDetails(EmployeeGoals employeeGoals,String userId) {
		LoggerUtil.infoLog(logger, "EmployeeGoalService Started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			employeeGoals.setDtCreated(new Date());
			employeeGoals.setDtModified(new Date());
			employeeGoals.setCreatedBy(employee.getPk());
			employeeGoals.setModifiedBy(employee.getPk());
			employeeGoalDAO.saveGoalDetails(employeeGoals, session);
			for (ProjectGoals projectGoals : employeeGoals.getProjectGoals()) {
				 projectGoals.setProject_comments(projectGoals.getProject_comments());
				 projectGoals.setProject_evaluation(projectGoals.getProject_evaluation());
				 projectGoals.setProject_pk(projectGoals.getProject_pk());
				 projectGoals.setDtCreated(new Date());
				 projectGoals.setDtModified(new Date());
				 projectGoals.setCreatedBy(employee.getPk());
				 projectGoals.setModifiedBy(employee.getPk());
				 employeeGoalDAO.saveProjectGoalDetails(projectGoals, session);
			}
			tx.commit();
			return employeeGoals;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Exception occured in saveGoalDetails in  EmployeeGoalServiceImpl and exception is ", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Exception occured in saveGoalDetails in  EmployeeGoalServiceImpl and exception is ", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

}
