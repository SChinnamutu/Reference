package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee_projects")
@SuppressWarnings("serial")
public class ProjectMembers  extends AbstractModel{

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "employee_pk")
	private Employee employeeId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "project_pk")
	private Projects projectId;
	
	@Column(name = "dt_started")
	private Date dtStarted;
	
	@Column(name = "dt_ended")
	private Date dtEnded;

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
	public Employee getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the projectId
	 */
	public Projects getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the dtStarted
	 */
	public Date getDtStarted() {
		return dtStarted;
	}

	/**
	 * @param dtStarted the dtStarted to set
	 */
	public void setDtStarted(Date dtStarted) {
		this.dtStarted = dtStarted;
	}

	/**
	 * @return the dtEnded
	 */
	public Date getDtEnded() {
		return dtEnded;
	}

	/**
	 * @param dtEnded the dtEnded to set
	 */
	public void setDtEnded(Date dtEnded) {
		this.dtEnded = dtEnded;
	}
}
