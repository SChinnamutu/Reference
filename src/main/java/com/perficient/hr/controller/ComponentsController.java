package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.Components;
import com.perficient.hr.service.ComponentsService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-components")
public class ComponentsController {
protected Logger logger = LoggerFactory.getLogger(ComponentsController.class);
	
	@Autowired
	private ComponentsService componentsService;
	
	@RequestMapping(value="/loadComponents",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadComponents(){
		return ResponseHandlingUtil.prepareResponse(componentsService.loadComponents());
	}
	
	@RequestMapping(value="/loadComponentById",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadComponentById(@RequestParam(value="id") String id){
		return ResponseHandlingUtil.prepareResponse(componentsService.loadComponentById(id));
	}
	
	@RequestMapping(value="/addComponent", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response addComponent(@RequestBody Components components, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(componentsService.addComponent(components, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/updateComponent", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response updateComponent(@RequestBody Components components, HttpServletRequest request) throws RecordNotFoundException {
		return ResponseHandlingUtil.prepareResponse(componentsService.updateComponent(components, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteComponent", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteComponent(@RequestBody Components component, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(componentsService.deleteComponent(component, PerfUtils.getUserId(request.getSession())));
	}
}
