package com.perficient.hr.utils;

import org.slf4j.Logger;

public class LoggerUtil {
	
	private LoggerUtil(){
		
	}
	
	public static void infoLog(Logger logger, String logText) {
		logger.info(logText);
	}
	
	public static void debugLog(Logger logger, String logText) {
		logger.debug(logText);
	}
	
	public static void errorLog(Logger logger, String logText, Exception exception) {
		logger.error(logText +" Exception is: "+ exception.getLocalizedMessage());
	}
	
}
