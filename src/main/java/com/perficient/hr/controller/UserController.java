package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.perficient.hr.dao.LoginDAO;
import com.perficient.hr.utils.PerfUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public LoginDAO loginDao;
	
	@RequestMapping(value="/validateSession",method=RequestMethod.GET)
	public ModelAndView validateSession(HttpServletRequest request){
		logger.info("validateSession");
		ModelAndView model = null;
		if(PerfUtils.getUserId(request.getSession()) == null) {
			request.getSession().invalidate();
			model = new ModelAndView("redirect:/logout");
		}
		return model;
	}
}
