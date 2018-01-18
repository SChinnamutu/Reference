package com.perficient.hr.dao.impl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.perficient.hr.dao.EmployeeGoalDAO;
import com.perficient.hr.model.EmployeeGoals;
import com.perficient.hr.model.ProjectGoals;
import com.perficient.hr.utils.LoggerUtil;



@Component
@Transactional
public class EmployeeGoalDAOImpl implements EmployeeGoalDAO{

	protected Logger logger = LoggerFactory.getLogger(EmployeeGoalDAOImpl.class);
	
	@Override
	public Object saveGoalDetails(EmployeeGoals employeeGoals,Session session) {
		Object object = null;
		try {
			object = session.save(employeeGoals);   
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.errorLog(logger, " Error ocuured in saveGoalDetails method in EmployeeGoalDAOImpl class While saving EmployeeGoals details", e);
			throw e;
		}
		return object;
	}
	
	@Override
	public Object saveProjectGoalDetails(ProjectGoals projectGoals,Session session) {
		Object object = null;
		try {
			object = session.save(projectGoals);   
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.errorLog(logger, " Error ocuured in saveProjectGoalDetails method in EmployeeGoalDAOImpl class While saving ProjectGoals details", e);
			throw e;
		}
		return object;
	}

}
