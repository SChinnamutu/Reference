package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.LoginDAO;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.User;

@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {
			 
	protected Logger logger = LoggerFactory.getLogger(LoginDAO.class);
	
	@SuppressWarnings("unchecked")
	@Override
    public User checkLogin(String userName, String userPwd, Session session) throws RecordNotFoundException {
		String sqlQuery =" from User as o where o.emailId=:email and o.pwd=:pwd";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("email", userName);
		query.setParameter("pwd", userPwd);
		List<User> list = query.list();
		if ((list != null) && (!list.isEmpty())) {
			return list.get(0);
		} else
			throw new RecordNotFoundException();
    }
}