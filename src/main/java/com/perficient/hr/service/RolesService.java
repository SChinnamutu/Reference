package com.perficient.hr.service;

import com.perficient.hr.model.Roles;

public interface RolesService {

    public Object loadRoles();
	
	public Object loadRolesById(String roleId);
	
	public Object addRoles(Roles role, String userId);
	
	public Object updateRoles(Roles role, String userId);
	
	public Object deleteRoles(Roles role, String userId);
	
}
