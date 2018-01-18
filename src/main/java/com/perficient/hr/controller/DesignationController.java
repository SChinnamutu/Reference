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
import com.perficient.hr.model.Designations;
import com.perficient.hr.service.DesignationsService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-designation")
public class DesignationController {

	protected Logger logger = LoggerFactory.getLogger(DesignationController.class);
	
	@Autowired
	private DesignationsService designationsService;
	
	@RequestMapping(value="/loadDesignations",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadDesignations(){
		return ResponseHandlingUtil.prepareResponse(designationsService.loadDesignations());
	}
	
	@RequestMapping(value="/loadDesignationById",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadDesignationById(@RequestParam(value="id") String id){
		return ResponseHandlingUtil.prepareResponse(designationsService.loadDesignationById(id));
	}
	
	@RequestMapping(value="/addDesignation", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response addDesignation(@RequestBody Designations designations, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(designationsService.addDesignation(designations, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/updateDesignation", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response updateDesignation(@RequestBody Designations designations, HttpServletRequest request) throws RecordNotFoundException {
		return ResponseHandlingUtil.prepareResponse(designationsService.updateDesignation(designations, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteDesignation", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteDesignation(@RequestBody Designations designation, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(designationsService.deleteDesignation(designation, PerfUtils.getUserId(request.getSession())));
	}
}
