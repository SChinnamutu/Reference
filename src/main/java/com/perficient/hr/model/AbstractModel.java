package com.perficient.hr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractModel implements Serializable {

	@Column(name = "active")
	private boolean active;
	
	@Column(name = "dt_created")
	private Date dtCreated;
	
	@Column(name = "dt_modified")
	private Date dtModified;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "modified_by")
	private Long modifiedBy;

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the dtCreated
	 */
	public Date getDtCreated() {
		return dtCreated;
	}

	/**
	 * @param dtCreated the dtCreated to set
	 */
	public void setDtCreated(Date dtCreated) {
		this.dtCreated = dtCreated;
	}

	/**
	 * @return the dtModified
	 */
	public Date getDtModified() {
		return dtModified;
	}

	/**
	 * @param dtModified the dtModified to set
	 */
	public void setDtModified(Date dtModified) {
		this.dtModified = dtModified;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedBy
	 */
	public Long getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
