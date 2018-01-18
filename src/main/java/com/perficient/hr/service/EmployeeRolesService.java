package com.perficient.hr.service;

import com.perficient.hr.form.EmployeeRolesForm;

public interface EmployeeRolesService {

	public Object saveEmpRoles(EmployeeRolesForm empRolesForm, String userId);
	
	public Object loadEmpByRoles(String roleId, String userId);
	
}
