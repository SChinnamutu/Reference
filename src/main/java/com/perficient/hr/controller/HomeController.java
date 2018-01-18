package com.perficient.hr.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
public class HomeController {

	protected Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private Properties prop;
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView doLogin(){
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/version",method=RequestMethod.GET)
	@ResponseBody
	public Response getVersion(){
		String version = null;
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/version.properties");
        this.prop = new Properties();
        try {
            this.prop.load(resourceAsStream);
            version = this.prop.getProperty("version").replace("-SNAPSHOT", "");
        } catch(IOException e){
        	logger.info("Unable to load project version.");
        }
		return ResponseHandlingUtil.prepareResponse(version);
	}
	
}
