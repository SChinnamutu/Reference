package com.perficient.hr.service;

import com.perficient.hr.model.Components;

public interface ComponentsService {

	public Object loadComponents();
	
	public Object loadComponentById(String componentId);
	
	public Object loadComponentByName(String componentName);
	
	public Object addComponent(Components component, String userId);
	
	public Object updateComponent(Components component, String userId);
	
	public Object deleteComponent(Components component, String userId);
	
}
