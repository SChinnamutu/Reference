package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.ProjectMembers;
import com.perficient.hr.service.ProjectMembersService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-projectmembers")
public class ProjectMembersController {

	@Autowired
	private ProjectMembersService projectMembersService;
	
	@RequestMapping(value="/loadAllProjectMembers",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadAllProjectMembers(){
		return ResponseHandlingUtil.prepareResponse(projectMembersService.loadAllProjectMembers());
	}
	
	@RequestMapping(value="/loadProjectMembersByProjectId",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadProjectMembersByProjectId(@RequestParam(value="projectId") String projectId) {
		return ResponseHandlingUtil.prepareResponse(projectMembersService.loadProjectMembersByProjectId(projectId));
	}
	
	@RequestMapping(value="/loadProjectMemberById", method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadProjectMemberById(@RequestParam(value="projectMemberId") String projectMemberId) {
		return ResponseHandlingUtil.prepareResponse(projectMembersService.loadProjectMemberById(projectMemberId));
	}
	
	@RequestMapping(value="/saveProjectMember", method=RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	@ResponseBody
	public Response saveProjectMember(@RequestBody ProjectMembers projectMembers, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(projectMembersService.saveProjectMember(projectMembers, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/updateProjectMember", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response updateProjectMember(@RequestBody ProjectMembers projectMembers, HttpServletRequest request) throws RecordNotFoundException {
		return ResponseHandlingUtil.prepareResponse(projectMembersService.updateProjectMember(projectMembers, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteProjectMember", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteDesignation(@RequestBody ProjectMembers projectMembers, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(projectMembersService.deleteProjectMember(projectMembers, PerfUtils.getUserId(request.getSession())));
	}
}
