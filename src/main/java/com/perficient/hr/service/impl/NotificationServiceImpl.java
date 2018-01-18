package com.perficient.hr.service.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.NotificationDAO;
import com.perficient.hr.service.NotificationService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;

@Repository("notificationService")
public class NotificationServiceImpl implements NotificationService{

protected Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;
	
	@Autowired
	NotificationDAO notificationDAO;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
	
    @Override
	public Object getNotificationCount(String employeeId) {
    	LoggerUtil.infoLog(logger, "Service to get Notification Count for the Employee " + employeeId);
    	Session session = null;
		try{
			session = sessionFactory.openSession();
			return notificationDAO.getNotificationCount(employeeId, session);
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to get Notification Count for the Employee " + employeeId, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get Notification Count for the Employee " + employeeId, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
}
