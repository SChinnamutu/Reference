package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.DesignationsDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.Designations;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("designationsDAO")
public class DesignationsDAOImpl implements DesignationsDAO {

	protected Logger logger = LoggerFactory.getLogger(DesignationsDAOImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;

	@Override
	@SuppressWarnings("unchecked")
	public List<Designations> loadDesignations(Session session) {
		String sqlQuery = " from Designations d where d.active=:active";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		return query.list();
	}

	@Override
	public Designations addDesignation(Designations designation , Session session){
		session.save(designation);
		return designation;
	}

	@Override
	public Designations loadDesignationById(String designationId, Session session) {
		return (Designations)session.get(Designations.class, Long.parseLong(designationId));
	}

	@Override
	public boolean updateDesignation(Designations designation, Session session) {
		session.merge(designation);
		return true;
	}
	
	@Override
	public Designations loadDesignationByName(String designationName, Session session) {
		String sqlQuery = " FROM Designations d where d.active=:active and d.designation=:designationName";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);		
		query.setParameter("designationName", designationName);
		return (Designations) query.uniqueResult();
	}

}
