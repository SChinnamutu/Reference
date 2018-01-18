package com.perficient.hr.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vw_employee_supervisor")
@SuppressWarnings("serial")
public class EmployeeView extends EmployeeModel {
	
	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "employeeId")
	private Set<EmployeeDesignation> employeeDesignation;
		
	@Column(name = "sup_firstname")
	private String superviserFirstName;
	
	@Column(name = "sup_lastname")
	private String superviserLastName;
	
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
	 * @return the superviserFirstName
	 */
	public String getSuperviserFirstName() {
		return superviserFirstName;
	}

	/**
	 * @param superviserFirstName the superviserFirstName to set
	 */
	public void setSuperviserFirstName(String superviserFirstName) {
		this.superviserFirstName = superviserFirstName;
	}

	/**
	 * @return the superviserLastName
	 */
	public String getSuperviserLastName() {
		return superviserLastName;
	}

	/**
	 * @param superviserLastName the superviserLastName to set
	 */
	public void setSuperviserLastName(String superviserLastName) {
		this.superviserLastName = superviserLastName;
	}

}
