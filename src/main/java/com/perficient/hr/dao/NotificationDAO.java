package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.Notification;

public interface NotificationDAO {

	public long getNotificationCount(String employeeId, Session session) throws GenericException;
	
	public List<Notification> loadNotificationsByGenericId(long genericId);
	
	public Object loadNotificationsToByGenericId(long genericId);
	
	public List<Employee> loadNotificationsToByGenericId(long genericId, Session session) throws GenericException;
	
	public List<Notification> loadNotificationListByGenericId(long genericId, Session session) throws GenericException;
	
	public boolean saveNotification(Notification notification);
	
	public boolean saveNotification(Notification notification, Session session) throws GenericException;
	
	public boolean updateNotification(Notification notification);
	
	public boolean updateNotification(Notification notification, Session session);
	
	public Notification loadByGenericAndEmployeeId(long genericId, long employeeId, boolean active);
	
	
	
}
