package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
@SuppressWarnings("serial")
public class Notification extends AbstractModel{

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "id_generic")
	private Long idGeneric;
	
	@Column(name = "notification_type")
	private String notificationType;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "notification_to")
	private Employee notificationTo;
	
	@Column(name = "notification_status")
	private String notificationStatus;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "flag")
	private int flag;
	
	@Column(name = "mail_status")
	private String mailStatus;
	
	
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
	 * @return the idGeneric
	 */
	public Long getIdGeneric() {
		return idGeneric;
	}

	/**
	 * @param idGeneric the idGeneric to set
	 */
	public void setIdGeneric(Long idGeneric) {
		this.idGeneric = idGeneric;
	}

	/**
	 * @return the notificationType
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * @return the notificationTo
	 */
	public Employee getNotificationTo() {
		return notificationTo;
	}

	/**
	 * @param notificationTo the notificationTo to set
	 */
	public void setNotificationTo(Employee notificationTo) {
		this.notificationTo = notificationTo;
	}

	/**
	 * @return the notificationStatus
	 */
	public String getNotificationStatus() {
		return notificationStatus;
	}

	/**
	 * @param notificationStatus the notificationStatus to set
	 */
	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

}
