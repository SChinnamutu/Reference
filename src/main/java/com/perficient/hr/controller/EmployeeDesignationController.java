package com.perficient.hr.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.service.EmployeeDesignationService;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-reportJobTitle")
public class EmployeeDesignationController {

	@Autowired
	private EmployeeDesignationService employeeDesignationService;
	
	@RequestMapping(value="/loadBySbu/{fromDate}/{toDate}/{sbu}/{designation}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadBySbu(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate, 
			@PathVariable("sbu") String sbu, @PathVariable("designation") String designation){
		return ResponseHandlingUtil.prepareResponse(employeeDesignationService.loadBySbu(fromDate, toDate, sbu, designation));
	}
	
}
