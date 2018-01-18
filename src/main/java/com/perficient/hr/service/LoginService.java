package com.perficient.hr.service;

import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.User;

public interface LoginService {    
	
	public User checkLogin(String userName, String userPassword) throws RecordNotFoundException;
	
}