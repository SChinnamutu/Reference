package com.perficient.hr.form;

import java.util.Date;

public class LeaveReport {

	private long totalHours;
	
	private long prevYearHours;
	
	private String employeeId;
	
	private String firstName;
	
	private String lastName;
	
	private Date joinDate;

	/**
	 * @return the totalHours
	 */
	public long getTotalHours() {
		return totalHours;
	}

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHours(long totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * @return the prevYearHours
	 */
	public long getPrevYearHours() {
		return prevYearHours;
	}

	/**
	 * @param prevYearHours the prevYearHours to set
	 */
	public void setPrevYearHours(long prevYearHours) {
		this.prevYearHours = prevYearHours;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the joinDate
	 */
	public Date getJoinDate() {
		return joinDate;
	}

	/**
	 * @param joinDate the joinDate to set
	 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
}
