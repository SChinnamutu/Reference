package com.perficient.hr.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee_roles")
@SuppressWarnings("serial")
public class EmployeeRoles extends AbstractModel {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "employee_pk")
	private Employee employee;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "role_pk")
	private Roles roleId;

	/**
	 * @return the pk
	 */
	public Long getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the roleId
	 */
	public Roles getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Roles roleId) {
		this.roleId = roleId;
	}

}
