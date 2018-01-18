package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.ComponentsDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.Components;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("componentsDAO")
public class ComponentsDAOImpl implements ComponentsDAO{

protected Logger logger = LoggerFactory.getLogger(ComponentsDAOImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;

	@Override
	@SuppressWarnings("unchecked")
	public List<Components> loadComponents(Session session) {
		String sqlQuery = " from Components d where d.active=:active";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		return query.list();
	}

	@Override
	public Components addComponent(Components component , Session session){
		session.save(component);
		return component;
	}

	@Override
	public Components loadComponentById(String componentId, Session session) {
		return (Components)session.get(Components.class, Long.parseLong(componentId));
	}

	@Override
	public boolean updateComponent(Components component, Session session) {
		session.merge(component);
		return true;
	}
	
	@Override
	public Components loadComponentByName(String componentName, Session session) {
		String sqlQuery = " FROM Components d where d.active=:active and d.compononentname=:componentName";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);		
		query.setParameter("compononentname", componentName);
		return (Components) query.uniqueResult();
	}
	
}
