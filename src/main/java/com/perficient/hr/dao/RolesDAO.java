package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.model.Roles;

public interface RolesDAO {

	public List<Roles> loadRoles(Session session);
	
	public Roles loadRolesById(String roleId, Session session);
	
	public Roles addRoles(Roles role , Session session);
	
	public boolean updateRoles(Roles role, Session session);
}
