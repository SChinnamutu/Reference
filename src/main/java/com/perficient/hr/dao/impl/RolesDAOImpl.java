package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.RolesDAO;
import com.perficient.hr.model.Roles;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("rolesDAO")
public class RolesDAOImpl implements RolesDAO{

	protected Logger logger = LoggerFactory.getLogger(RolesDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Roles> loadRoles(Session session) {
		String sqlQuery = " from Roles d where d.active=:active";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		return query.list();
	}

	@Override
	public Roles loadRolesById(String roleId, Session session) {
		return (Roles)session.get(Roles.class, Long.parseLong(roleId));
	}
	
	@Override
	public Roles addRoles(Roles role , Session session){
		session.save(role);
		return role;
	}

	@Override
	public boolean updateRoles(Roles role, Session session) {
		session.merge(role);
		return false;
	}
}
