package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.model.Designations;

public interface DesignationsDAO {
	
	public List<Designations> loadDesignations(Session session);
	
	public Designations loadDesignationById(String designationId, Session session);
	
	public Designations loadDesignationByName(String designationName, Session session);
	
	public Designations addDesignation(Designations designation, Session session);
	
	public boolean updateDesignation(Designations designation, Session session);
	
}
