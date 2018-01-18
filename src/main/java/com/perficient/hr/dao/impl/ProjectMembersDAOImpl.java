package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.ProjectMembersDAO;
import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.ProjectMembers;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("projectMembersDAO")
public class ProjectMembersDAOImpl implements ProjectMembersDAO{

protected Logger logger = LoggerFactory.getLogger(ProjectMembersDAOImpl.class);

	@Autowired
	EmployeeDAO employeeDAO;

    @SuppressWarnings("unchecked")
	@Override
	public List<ProjectMembers> loadAllProjectMembers(Session session) throws GenericException {
		String sqlQuery = " from ProjectMembers pm where pm.active=:active";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		return  query.list();
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMembers> loadProjectMembersByProjectId(String projectPk, Session session) throws GenericException {
		String sqlQuery = " from ProjectMembers as pm where pm.projectId.pk=:projectPk";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("projectPk", Long.parseLong(projectPk));
		return  query.list();
	}

	@Override
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers, Session session) throws GenericException {
		session.save(projectMembers);
		return projectMembers;
	}

	@Override
	public ProjectMembers loadProjectMemberById(String projectMemberId, Session session) throws GenericException {
		return  (ProjectMembers)session.get(ProjectMembers.class, Long.parseLong(projectMemberId));
	}

	@Override
	public boolean updateProjectMember(ProjectMembers projectMembers, Session session) throws GenericException {
		session.merge(projectMembers);
		return true;
	}
}
