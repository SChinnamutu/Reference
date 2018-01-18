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
import com.perficient.hr.model.Roles;
import com.perficient.hr.service.RolesService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-roles")
public class RolesController {

	protected Logger logger = LoggerFactory.getLogger(DesignationController.class);
	
	@Autowired
	private RolesService rolesService;
	
	@RequestMapping(value="/loadRoles",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadRoles(){
		return ResponseHandlingUtil.prepareResponse(rolesService.loadRoles());
	}
	
	@RequestMapping(value="/loadRolesById",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadRolesById(@RequestParam(value="id") String roleId){
		return ResponseHandlingUtil.prepareResponse(rolesService.loadRolesById(roleId));
	}
	
	@RequestMapping(value="/addRoles", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response addRoles(@RequestBody Roles role, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(rolesService.addRoles(role, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/updateRoles", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response updateRoles(@RequestBody Roles role, HttpServletRequest request) throws RecordNotFoundException {
		return ResponseHandlingUtil.prepareResponse(rolesService.updateRoles(role, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteRoles", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteRoles(@RequestBody Roles role, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(rolesService.deleteRoles(role, PerfUtils.getUserId(request.getSession())));
	}
}
