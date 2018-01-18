package com.perficient.hr.service;

import com.perficient.hr.model.ProjectMembers;

public interface ProjectMembersService {
	
	public Object loadAllProjectMembers();

	public Object loadProjectMembersByProjectId(String projectPk);
	
	public Object loadProjectMemberById(String projectMemberId);
	
	public Object saveProjectMember(ProjectMembers projectMembers, String userId);
	
	public Object updateProjectMember(ProjectMembers projectMembers, String userId);
	
	public Object deleteProjectMember(ProjectMembers projectMembers, String userId);
	
}
