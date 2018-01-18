package com.perficient.hr.service;

import com.perficient.hr.model.Projects;

public interface ProjectService {

    public Object loadProjects();
	
	public Object addProject(Projects project, String userId);
	
	public Object updateProject(Projects project, String userId);
	
	public Object deleteProject(Projects project, String userId);
	
	public Object loadProjectById(String projectPk);
	
}
