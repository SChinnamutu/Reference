package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "employee")
@SuppressWarnings("serial")
public class Employee extends EmployeeModel {
	
	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Transient
	private String superviserFirstName;
	
	@Transient
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
	
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			Employee employee = (Employee) object;
			if (this.pk == employee.getPk()) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 7 * hash + this.pk.hashCode();
		return hash;
	}

}
