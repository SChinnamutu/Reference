package com.perficient.hr.service;

import com.perficient.hr.model.Employee;
import com.perficient.hr.model.Notification;

public interface EmployeeService {
	
	public Object loadByUserId(String employeeId);
	
	public Object loadById(String employeeId);
	
	public Object loadEmployees();
	
	public Object loadAllEmployees();
	
	public Object updateEmployee(Employee employee, String userId);
	
	public Object updateNotification(Notification notification);
	
	public Object addEmployee(Employee employee, String userId);
	
	public Object loadEmployeeByDesHistory(String stDate, String endDate, String desingation);

}
