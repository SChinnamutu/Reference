package com.perficient.hr.dao;

import org.hibernate.Session;

import com.perficient.hr.model.EmployeeGoals;
import com.perficient.hr.model.ProjectGoals;

public interface EmployeeGoalDAO {

	public Object saveGoalDetails(EmployeeGoals employeeGoals,Session session);
	public Object saveProjectGoalDetails(ProjectGoals projectGoals,Session session);
}
