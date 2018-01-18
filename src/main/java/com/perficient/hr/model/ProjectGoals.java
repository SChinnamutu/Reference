package com.perficient.hr.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="project_goals")
public class ProjectGoals extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	
	@Column(name = "project_pk")
	private Long project_pk;
	
	@Column(name = "project_comments")
	private String project_comments;
	
	@Column(name = "project_evaluation")
	private String project_evaluation;
	
	@Column(name = "project_self_evaluation")
	private String project_self_evaluation;
	

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "pk")
	private Set<Projects> projects = new HashSet<Projects>(0);

	public Long getPk() {
		return pk;
	}


	public void setPk(Long pk) {
		this.pk = pk;
	}



	public Long getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}


	public String getProject_self_evaluation() {
		return project_self_evaluation;
	}


	public void setProject_self_evaluation(String project_self_evaluation) {
		this.project_self_evaluation = project_self_evaluation;
	}


	public Long getProject_pk() {
		return project_pk;
	}


	public void setProject_pk(Long project_pk) {
		this.project_pk = project_pk;
	}

	public String getProject_comments() {
		return project_comments;
	}


	public void setProject_comments(String project_comments) {
		this.project_comments = project_comments;
	}


	public String getProject_evaluation() {
		return project_evaluation;
	}


	public void setProject_evaluation(String project_evaluation) {
		this.project_evaluation = project_evaluation;
	}
	
	public Set<Projects> getProjects() {
		return projects;
	}


	public void setProjects(Set<Projects> projects) {
		this.projects = projects;
	}

}
