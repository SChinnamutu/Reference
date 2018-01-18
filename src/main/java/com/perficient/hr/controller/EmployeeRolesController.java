package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import com.perficient.hr.form.EmployeeRolesForm;
import com.perficient.hr.service.EmployeeRolesService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-emproles")
public class EmployeeRolesController {

	protected Logger logger = LoggerFactory.getLogger(EmployeeRolesController.class);
	
	@Autowired
	private EmployeeRolesService employeeRolesService;
	
	@RequestMapping(value="/saveEmpRoles", method=RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	@ResponseBody
	public Response saveEmpRoles(@RequestBody EmployeeRolesForm empRolesForm, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(employeeRolesService.saveEmpRoles(empRolesForm, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadEmpByRoles",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadEmpByRoles(@RequestParam(value="roleId") String roleId, HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse(employeeRolesService.loadEmpByRoles(roleId, PerfUtils.getUserId(request.getSession())));
	}	
}
