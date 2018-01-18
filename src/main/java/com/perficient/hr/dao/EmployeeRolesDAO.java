package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.model.EmployeeRoles;
import com.perficient.hr.model.Roles;

public interface EmployeeRolesDAO {

	public EmployeeRoles saveEmpRoles(EmployeeRoles employeeRoles, Session session);
	
	public List<EmployeeRoles> loadEmpRolesByRoles(String roleId, Session session);
		
	public int removeEmpRoles(Roles role, Session session);

	public int removeEmpRolesByEmpIds(Roles role, Integer empId, Session session);

	public int removeEmpRoles(Long roldId, String empIds, Session session);
	
}