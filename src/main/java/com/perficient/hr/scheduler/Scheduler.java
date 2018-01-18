package com.perficient.hr.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.schedulerService.SchedulerService;
import com.perficient.hr.utils.LoggerUtil;



public class Scheduler {
	
	
	protected Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	@Autowired
	private SchedulerService iSchedulerService;
	
	 //@Scheduled(cron="*/25 * * * * ?")
	 public void sendNotification(){
		 LoggerUtil.infoLog(logger, " Send mail scheduler started");
		 iSchedulerService.getNotificationDetails();
		 iSchedulerService.sendEmail();
		 LoggerUtil.infoLog(logger, " Send mail scheduler completes its process");
	 }
	 
	
}
