package com.perficient.hr.model.type;

public enum LeaveType {

	PTO("PTO"),
	UNPLANNED_PTO("Unplanned PTO"),
	WFH("WFH"),
	SABATICAL("SABATICAL"),
	LOP("LOP"),
	MATERNITY_PAID_LEAVE("MATERNITY PAID LEAVE"),
	MATERNITY_UNPAID_LEAVE("MATERNITY UNPAID LEAVE"),
	COMPENSATORY_OFF("COMPENSATORY OFF"),
	PERSONAL_LEAVE("PERSONAL LEAVE"),
	ALL_PTO("ALL_PTO");
	
	private String leavesType;
	
	LeaveType(String leavesType){
		this.leavesType = leavesType;
	}
	
	public String getLeaveType() {
        return leavesType;
    }
	
}
