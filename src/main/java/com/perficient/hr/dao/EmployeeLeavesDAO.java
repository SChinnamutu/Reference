package com.perficient.hr.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;

import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.EmployeeLeaveDetails;
import com.perficient.hr.model.EmployeeLeaveMaintenance;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.Notification;

public interface EmployeeLeavesDAO {

	public List<EmployeeLeaves> loadAllLeaves(String leaveType, String calYear, Session session) throws ParseException;
	
	public EmployeeLeaves loadLeaveById(String leaveId, Session session);
	
	public EmployeeLeaveDetails loadLeaveDetailsByDtAndLeaveId(Long empLeaveId, Date date, Session session);
	
	public List<EmployeeLeaves> loadMyLeaves(String leaveType, String calYear, String employeeId, Session session) throws ParseException;
	
	public EmployeeLeaves saveLeave(EmployeeLeaves employeeLeaves, Session session);
	
	public EmployeeLeaveDetails saveLeaveDetails(EmployeeLeaveDetails employeeLeavesDetails, Session session);
	
	public boolean updateLeave(EmployeeLeaves employeeLeaves, Session session);
	
	public boolean updateLeaveDetails(Long empLeaveId, boolean isActive, Session session);
	
	public boolean carryLeaves(String year, String employeeId, Session session) throws RecordExistsException;
	
	public EmployeeLeaveMaintenance getCarryLeaves(String employeeId, String year, Session session);
	
	public Long getLeaveBalance(String leaveType, String calYear, String calMonth, String employeeId, 
			int totalLeaves, Session session) throws ParseException;
	
	public List<EmployeeLeaves> loadLeaveReport(EmployeeLeaves employeeLeaves, Session session);
	
	public List<EmployeeLeaves> loadLeaveReportByEmpId(EmployeeLeaves employeeLeaves, Session session);

	public void saveLeave(Session session, Row row) throws ParseException;
	
	public Object getNotificationDetails(Session session);
		
	public List<EmployeeLeaves> isLeaveAppliedExactly(EmployeeLeaves employeeLeaves, Session session,String flag);

	public Object updateNotification(Notification notification, Session session);

	

}
