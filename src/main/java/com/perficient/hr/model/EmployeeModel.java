package com.perficient.hr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("serial")
public class EmployeeModel extends AbstractModel implements Serializable{

	@Column(name = "employee_id")
	private String employeeId;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;

	@Column(name = "middlename")
	private String middleName;
	
	@Column(name = "contact_no")
	private String contactNo;
	
	@Column(name = "email")
	private String email;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "designation")
	private Designations designations;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "pincode")
	private String pincode;
	
	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "supervisor")
	private Long supervisor;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "blood_group")
	private String bloodGroup;
	
	@Column(name = "pan_card_no")
	private String panCard;
	
	@Column(name = "joindate")
	private Date joinDate;
	
	@Column(name = "skills")
	private String skills;
	
	@Column(name = "last_working_Date")
	private Date lastWorkDate;
		
	@Column(name = "billable")
	private int billable;
	
	@Column(name = "gender")
	private String gender;

	@Column(name="designation_effective_date")
	private Date designation_effective_date;
	
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
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the designations
	 */
	public Designations getDesignations() {
		return designations;
	}

	/**
	 * @param designations the designations to set
	 */
	public void setDesignations(Designations designations) {
		this.designations = designations;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the supervisor
	 */
	public Long getSupervisor() {
		return supervisor;
	}

	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(Long supervisor) {
		this.supervisor = supervisor;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the bloodGroup
	 */
	public String getBloodGroup() {
		return bloodGroup;
	}

	/**
	 * @param bloodGroup the bloodGroup to set
	 */
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	/**
	 * @return the panCard
	 */
	public String getPanCard() {
		return panCard;
	}

	/**
	 * @param panCard the panCard to set
	 */
	public void setPanCard(String panCard) {
		this.panCard = panCard;
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

	/**
	 * @return the skills
	 */
	public String getSkills() {
		return skills;
	}

	/**
	 * @param skills the skills to set
	 */
	public void setSkills(String skills) {
		this.skills = skills;
	}

	/**
	 * @return the lastWorkDate
	 */
	public Date getLastWorkDate() {
		return lastWorkDate;
	}

	/**
	 * @param lastWorkDate the lastWorkDate to set
	 */
	public void setLastWorkDate(Date lastWorkDate) {
		this.lastWorkDate = lastWorkDate;
	}

	/**
	 * @return the billable
	 */
	public int getBillable() {
		return billable;
	}

	/**
	 * @param billable the billable to set
	 */
	public void setBillable(int billable) {
		this.billable = billable;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the designation_effective_date
	 */
	public Date getDesignation_effective_date() {
		return designation_effective_date;
	}

	/**
	 * @param designation_effective_date the designation_effective_date to set
	 */
	public void setDesignation_effective_date(Date designation_effective_date) {
		this.designation_effective_date = designation_effective_date;
	}
	
}
