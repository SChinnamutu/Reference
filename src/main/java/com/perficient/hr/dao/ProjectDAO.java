package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.Projects;

public interface ProjectDAO {

    public List<Projects> loadProjects(Session session) throws  GenericException;
	
	public Projects addProject(Projects project, Session session) throws  GenericException;
	
	public boolean updateProject(Projects project, Session session) throws  GenericException;
	
	public boolean deleteProject(Projects project, Session session) throws  GenericException;
	
	public Projects loadProjectById(String projectPk, Session session) throws  GenericException;
	
}
