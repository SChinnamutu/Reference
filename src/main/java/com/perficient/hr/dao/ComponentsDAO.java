package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.model.Components;

public interface ComponentsDAO {

	public List<Components> loadComponents(Session session);
	
	public Components loadComponentById(String componentId, Session session);
	
	public Components loadComponentByName(String componentName, Session session);
	
	public Components addComponent(Components component, Session session);
	
	public boolean updateComponent(Components component, Session session);
	
}
