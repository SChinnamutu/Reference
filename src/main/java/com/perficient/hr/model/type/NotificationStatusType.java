package com.perficient.hr.model.type;

public enum NotificationStatusType {

	SUBMITTED("SUBMITTED"),
	PENDING("PENDING"),
	APPROVED("APPROVED"),
	REJECTED("REJECTED");
	
	private String statusType;
	
	NotificationStatusType(String statusType){
		this.statusType = statusType;
	}
	
	public String getNotificationStatusType() {
        return statusType;
    }
	
}
