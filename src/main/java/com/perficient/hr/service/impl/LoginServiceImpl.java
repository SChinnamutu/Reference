package com.perficient.hr.service.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.LoginDAO;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.User;
import com.perficient.hr.service.LoginService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;

@Repository("loginService")
public class LoginServiceImpl implements LoginService {
			 
	protected Logger logger = LoggerFactory.getLogger(LoginService.class);
	
    @Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;
    
    @Autowired
    private LoginDAO loginDAO;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
  
    protected Session getSession(){
       return sessionFactory.openSession();
    }

	@Override
    public User checkLogin(String userName, String userPwd) throws RecordNotFoundException{
		LoggerUtil.infoLog(logger, "Service to Check Login for the User : "+userName);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return loginDAO.checkLogin(userName, userPwd, session);
		} catch (RecordNotFoundException e) {
			LoggerUtil.errorLog(logger, "Unable to check user login credentials for the User: "+userName , e);
			throw e;
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
    }
}