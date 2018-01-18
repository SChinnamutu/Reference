package com.perficient.hr.service;

import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.Notification;

public interface EmployeeLeavesService {

	public Object parseDocument(String fileName);
	
	public Object loadAllLeaves(String leaveType, String calYear);
	
	public Object loadLeaveById(String leaveId);
	
	public Object loadMyLeaves(String leaveType, String calYear, String employeeId);
	
	public Object carryLeaves(String calYear, String employeeId);
	
	public Object getCarryLeaves(String calYear, String employeeId);
	
	public Object applyLeave(EmployeeLeaves employeeLeaves, String userId);
	
	public Object updateLeave(EmployeeLeaves employeeLeaves, String userId);
	
	public Object deleteLeave(EmployeeLeaves employeeLeaves, String userId);
	
	public Object getLeaveBalance(String leaveType, String calYear, String calMonth, String employeeId, int totalLeaves);
	
	public Object loadLeaveReport(EmployeeLeaves employeeLeaves);
	
	public Object loadAllLeaveReport(EmployeeLeaves employeeLeaves);
	
	public Object getNotificationDetails();
	
	public Object updateNotification(Notification notification);
	
}
