package com.perficient.hr.dao;

import org.hibernate.Session;

import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.User;

public interface LoginDAO {    
	
	public User checkLogin(String userName, String userPassword, Session session) throws RecordNotFoundException;
	
}