package com.perficient.hr.model.type;

public enum MailStatusType {

	CONFIRMED("CONFIRMED"),
	REQUEST("REQUEST"),
	CANCEL("CANCEL"),
	CANCELLED("CANCELLED");
	
	private String statusType;
	
	MailStatusType(String statusType){
		this.statusType = statusType;
	}
	
	public String getMailStatusType() {
        return statusType;
    }
	
}
