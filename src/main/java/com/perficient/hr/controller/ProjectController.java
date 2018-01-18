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
import com.perficient.hr.model.Projects;
import com.perficient.hr.service.ProjectService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-projects")
public class ProjectController {

protected Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="/loadProjects",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadProjects(){
		return ResponseHandlingUtil.prepareResponse(projectService.loadProjects());
	}
	
	@RequestMapping(value="/loadProjectById",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadProjectById(@RequestParam(value="projectPk") String projectPk){
		return ResponseHandlingUtil.prepareResponse(projectService.loadProjectById(projectPk));
	}
	
	@RequestMapping(value="/addProject", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response addProject(@RequestBody Projects project, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(projectService.addProject(project, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/updateProject", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response updateProject(@RequestBody Projects project, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(projectService.updateProject(project, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteProject", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteProject(@RequestBody Projects project, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(projectService.deleteProject(project, PerfUtils.getUserId(request.getSession())));
	}
}
