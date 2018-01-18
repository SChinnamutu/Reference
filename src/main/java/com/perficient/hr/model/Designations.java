package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "designations")
@SuppressWarnings("serial")
public class Designations  extends AbstractModel {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "sbu")
	private String sbu;
			
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
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the sbu
	 */
	public String getSbu() {
		return sbu;
	}

	/**
	 * @param sbu the sbu to set
	 */
	public void setSbu(String sbu) {
		this.sbu = sbu;
	}
	
}
