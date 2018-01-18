package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.Employee;
import com.perficient.hr.service.EmployeeService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="/loadEmployee",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadEmployee(HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse(employeeService.loadById(PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadEmployeeById",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadEmployeeById(@RequestParam(value="employeeId") String employeeId){
		return ResponseHandlingUtil.prepareResponse(employeeService.loadById(employeeId));
	}
	
	@RequestMapping(value="/loadEmployees",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadEmployees(){
		return ResponseHandlingUtil.prepareResponse(employeeService.loadEmployees());
	}
	
	@RequestMapping(value="/loadAllEmployees",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadAllEmployees(){
		return ResponseHandlingUtil.prepareResponse(employeeService.loadAllEmployees());
	}
	
	@RequestMapping(value="/updateEmployee", method=RequestMethod.PUT)
	@Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Response updateEmployee(@RequestBody Employee employee, HttpServletRequest request, HttpServletResponse response) throws RecordNotFoundException{
		if(employeeService.loadById(String.valueOf(employee.getPk())) == null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new RecordNotFoundException();
		}
		return ResponseHandlingUtil.prepareResponse(employeeService.updateEmployee(employee, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/addEmployee", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response addEmployee(@RequestBody Employee employee, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(employeeService.addEmployee(employee, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadEmployeeByDesHistory/{fromDate}/{toDate}/{designation}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadEmployeeByDesHistory(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate, 
			@PathVariable("designation") String designation){
		return ResponseHandlingUtil.prepareResponse(employeeService.loadEmployeeByDesHistory(fromDate, toDate, designation));
	}
}