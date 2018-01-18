package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.ProjectMembers;

public interface ProjectMembersDAO {
	
	public List<ProjectMembers> loadAllProjectMembers(Session session) throws GenericException;

	public List<ProjectMembers> loadProjectMembersByProjectId(String projectPk, Session session) throws GenericException;
	
	public ProjectMembers loadProjectMemberById(String projectMemberId, Session session) throws GenericException;
	
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers, Session session) throws GenericException;
	
	public boolean updateProjectMember(ProjectMembers projectMembers, Session session) throws GenericException;
	
	
}
