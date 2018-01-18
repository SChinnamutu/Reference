package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.model.EmployeeGoals;
import com.perficient.hr.service.EmployeeGoalService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;


@Controller
public class EmployeeGoalController {

	@Autowired
	private EmployeeGoalService employeeGoalService;
	
    @RequestMapping(value="/saveGoalDetails",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response saveGoalDetails(@RequestBody EmployeeGoals employeeGoals,HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(employeeGoalService.saveGoalDetails(employeeGoals, PerfUtils.getUserId(request.getSession())));
	}


	
	
}
