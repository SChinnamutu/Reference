package com.perficient.hr.service;

import com.perficient.hr.model.Designations;

public interface DesignationsService {
	
	public Object loadDesignations();
	
	public Object loadDesignationById(String designationId);
	
	public Object loadDesignationByName(String designationName);
	
	public Object addDesignation(Designations designation, String userId);
	
	public Object updateDesignation(Designations designation, String userId);
	
	public Object deleteDesignation(Designations designation, String userId);
	
}
