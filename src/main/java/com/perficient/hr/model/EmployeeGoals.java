package com.perficient.hr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="employee_goals")
public class EmployeeGoals extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="pk")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long pk;
	
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	@Column(name="goalYear")
	private String goalYear;
	
	@Column(name="goal_description")
	private String goal_description;
	
	@Column(name="utilization_evaluation")
	private Long utilization_evaluation;
	
	@Column(name="utilization_self_evaluation")
	private Long utilization_self_evaluation;
	
	
	@Column(name="utilization_comments")
	private String utilization_comments;
	
	@Column(name="general_evaluation")
	private Long general_evaluation;
	
	@Column(name="general_self_evaluation")
	private Long general_self_evaluation;
	
	@Column(name="general_comments")
	private String general_comments;
	
	@Column(name="professional_development_evaluation")
	private Long professional_development_evaluation;
	
	@Column(name="professional_development_self_evaluation")
	private Long professional_development_self_evaluation;
	
	@Column(name="professional_development_comments")
	private  String professional_development_comments;
	
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy = "employeeId")
	private List<ProjectGoals> projectGoals = new ArrayList<ProjectGoals>();

	public Long getUtilization_self_evaluation() {
		return utilization_self_evaluation;
	}

	public void setUtilization_self_evaluation(Long utilization_self_evaluation) {
		this.utilization_self_evaluation = utilization_self_evaluation;
	}

	public Long getGeneral_self_evaluation() {
		return general_self_evaluation;
	}

	public void setGeneral_self_evaluation(Long general_self_evaluation) {
		this.general_self_evaluation = general_self_evaluation;
	}

	public Long getProfessional_development_self_evaluation() {
		return professional_development_self_evaluation;
	}

	public void setProfessional_development_self_evaluation(
			Long professional_development_self_evaluation) {
		this.professional_development_self_evaluation = professional_development_self_evaluation;
	}

	

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

	public String getGoalYear() {
		return goalYear;
	}

	public void setGoalYear(String goalYear) {
		this.goalYear = goalYear;
	}

	public String getGoal_description() {
		return goal_description;
	}

	public void setGoal_description(String goal_description) {
		this.goal_description = goal_description;
	}

	public Long getUtilization_evaluation() {
		return utilization_evaluation;
	}

	public void setUtilization_evaluation(Long utilization_evaluation) {
		this.utilization_evaluation = utilization_evaluation;
	}

	public String getUtilization_comments() {
		return utilization_comments;
	}

	public void setUtilization_comments(String utilization_comments) {
		this.utilization_comments = utilization_comments;
	}

	public Long getGeneral_evaluation() {
		return general_evaluation;
	}

	public void setGeneral_evaluation(Long general_evaluation) {
		this.general_evaluation = general_evaluation;
	}

	public String getGeneral_comments() {
		return general_comments;
	}

	public void setGeneral_comments(String general_comments) {
		this.general_comments = general_comments;
	}

	public Long getProfessional_development_evaluation() {
		return professional_development_evaluation;
	}

	public void setProfessional_development_evaluation(
			Long professional_development_evaluation) {
		this.professional_development_evaluation = professional_development_evaluation;
	}

	public String getProfessional_development_comments() {
		return professional_development_comments;
	}

	public void setProfessional_development_comments(
			String professional_development_comments) {
		this.professional_development_comments = professional_development_comments;
	}

	public List<ProjectGoals> getProjectGoals() {
		return projectGoals;
	}

	public void setProjectGoals(List<ProjectGoals> projectGoals) {
		this.projectGoals = projectGoals;
	}

	

}
