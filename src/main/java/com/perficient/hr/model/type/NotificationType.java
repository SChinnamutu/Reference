package com.perficient.hr.model.type;

public enum NotificationType {

	PTO("PTO"),
	UNPLANNED_PTO("Unplanned PTO"),
	WFH("WFH");
	
	private String leaveType;
	
	NotificationType(String leaveType){
		this.leaveType = leaveType;
	}
	
	public String getLeaveType() {
        return leaveType;
    }
	
}
