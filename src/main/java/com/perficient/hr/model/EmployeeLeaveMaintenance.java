package com.perficient.hr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_leaves_maintainance")
@SuppressWarnings("serial")
public class EmployeeLeaveMaintenance extends AbstractModel implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "carried_on")
	private int carriedOn;
	
	@Column(name = "bye_out")
	private int byeOut;

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
	 * @return the employeeId
	 */
	public Long getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the carriedOn
	 */
	public int getCarriedOn() {
		return carriedOn;
	}

	/**
	 * @param carriedOn the carriedOn to set
	 */
	public void setCarriedOn(int carriedOn) {
		this.carriedOn = carriedOn;
	}

	/**
	 * @return the byeOut
	 */
	public int getByeOut() {
		return byeOut;
	}

	/**
	 * @param byeOut the byeOut to set
	 */
	public void setByeOut(int byeOut) {
		this.byeOut = byeOut;
	}
	
}
