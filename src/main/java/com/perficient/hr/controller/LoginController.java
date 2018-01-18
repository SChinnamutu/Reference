package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.perficient.hr.form.LoginForm;
import com.perficient.hr.model.User;
import com.perficient.hr.service.LoginService;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;

@Controller
public class LoginController {

	protected Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	public LoginService loginService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView doLogin(){
		return new ModelAndView(PerfHrConstants.LOGIN_MODEL);
	}

	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request, 
			@ModelAttribute("loginBean")LoginForm loginForm) {
		ModelAndView model = null;
		try {
			HttpSession session = request.getSession();
			logger.info("Random sess :"+session.getAttribute("ran"));
			logger.info("Authenticating User :"+loginForm.getUsername());
			User userExists = loginService.checkLogin(loginForm.getUsername(),loginForm.getPassword());
			if(userExists != null){
				session.setAttribute(PerfHrConstants.USER_ID, userExists.getEmployeePk());
				logger.info("Authentication successful. Redirecting to home page.");
				model = new ModelAndView("redirect:/home");
			} else {
				logger.error("Invalid Credentials provided for User: "+loginForm.getUsername());
				model = new ModelAndView(PerfHrConstants.LOGIN_MODEL);
				model.addObject("msg", "Invalid UserName/Password!");
			}
		} catch(Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Authenticate User: "+loginForm.getUsername() , e);
			model = new ModelAndView(PerfHrConstants.LOGIN_MODEL);
			model.addObject("msg", "An Error occured during login!");
		}
		return model;
	}
	
	@RequestMapping(value="/mobileLogin",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public String authenticateUser(HttpServletRequest request,
			@RequestHeader(value="username") String username, @RequestHeader(value="password") String password){
		String returnVal="failed";
		logger.info("Authenticating User :"+username);
		User userExists = null;
		try {
			userExists = loginService.checkLogin(username, password);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Authenticate User: "+username , e);
			return returnVal; 
		}
		if(userExists != null){
			HttpSession session = request.getSession();
			session.setAttribute(PerfHrConstants.USER_ID, userExists.getEmployeePk());
			returnVal = "success";
			logger.info("Authentication successful");
		} else {
			logger.info("Invalid Credentials provided for User: "+password);
		}
		return returnVal;
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView doLogut(HttpServletRequest request){
		request.getSession().invalidate();
		logger.info("User logged out successfully");
		ModelAndView model = new ModelAndView(PerfHrConstants.LOGIN_MODEL);
		model.addObject("msg", "Logged out Successfully!");
		return model;
	}
}
