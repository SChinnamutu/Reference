package com.perficient.hr.form;

import java.util.List;

import com.perficient.hr.model.Employee;
import com.perficient.hr.model.Roles;

public class EmployeeRolesForm {

	private List<Employee> employee;
	
	private Roles role;

	/**
	 * @return the employee
	 */
	public List<Employee> getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	/**
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Roles role) {
		this.role = role;
	}
	
}
