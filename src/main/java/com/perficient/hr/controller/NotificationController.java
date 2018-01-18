package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.service.NotificationService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value="/loadNotificationCount",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadNotificationCount(HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse(notificationService.getNotificationCount(PerfUtils.getUserId(request.getSession())));
	}
	
}
