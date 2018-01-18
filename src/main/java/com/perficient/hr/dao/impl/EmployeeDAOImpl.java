package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeView;
import com.perficient.hr.model.Notification;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("employeeDAO")
public class EmployeeDAOImpl implements EmployeeDAO{

	protected Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    @Override
    public EmployeeView loadByUserId(String pk, Session session) {
    	String sqlQuery =" from EmployeeView as o where o.pk=:pk and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("pk", Long.parseLong(pk));
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return (EmployeeView)query.uniqueResult();
	}

    @Override
   	public Employee loadById(String pk, Session session){
   		return (Employee)loadEmployee(pk, session).uniqueResult();
   	}
    
    private Query loadEmployee(String pk, Session session){
    	logger.info("Loading employee record for: "+pk);
   		String sqlQuery =" from Employee as o where o.pk=:pk and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("pk", Long.parseLong(pk));
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
   		return query;
    }
    
    @Override
   	public Employee loadByEmployeeId(String employeeId, Session session){
   		logger.info("Loading employee record for EmployeeId: "+employeeId);
   		String sqlQuery =" from Employee as o where o.employeeId=:employeeId and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("employeeId", employeeId);
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
   		return (Employee)query.uniqueResult();
   	}
    
    @SuppressWarnings("unchecked")
	@Override
	public List<EmployeeView> loadEmployees(Session session){
		String sqlQuery =" from EmployeeView e where e.active=:active order by e.firstName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return query.list();
	}

	@Override
	public boolean updateEmployee(Employee employee, String userId, Session session) {
		session.merge(employee);
		return true;
	}

	@Override
	public boolean addEmployee(Employee employee, Session session){
		session.save(employee);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeView> loadEmployeeByDesHistory(String stDate,
			String endDate, String designationName, Session session){
		Query query = session.createSQLQuery(
				"CALL getEmployeeDesignationByRange(:stDate, :endDate, :designationName)")
				.addEntity(EmployeeView.class)
				.setParameter("stDate", DateUtils.convertMilliSecondsToDate(stDate))
				.setParameter("endDate", DateUtils.convertMilliSecondsToDate(endDate))
				.setParameter("designationName", designationName);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> loadAllEmployees(Session session) {
		String sqlQuery =" from Employee e where e.active=:active order by e.firstName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return query.list();
	}

	@Override
	public boolean updateNotification(Notification notification, Session session) {
		session.merge(notification);
		return true;
	}
}
