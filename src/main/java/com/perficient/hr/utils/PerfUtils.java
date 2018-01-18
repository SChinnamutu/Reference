package com.perficient.hr.utils;

import javax.servlet.http.HttpSession;

public class PerfUtils {

	private PerfUtils(){
		
	}
	
	public static String getUserId(HttpSession session){
		return session.getAttribute("29").toString();
	}
	
	public static String capitalizeFully(String str){
		String[] valArray= str.split(" ");
        StringBuffer strBuffer= new StringBuffer();
        for (int i = 0; i < valArray.length; i++) {
        	strBuffer.append(Character.toUpperCase(valArray[i].charAt(0))).append(valArray[i].substring(1)).append(" ");
        }  
        return strBuffer.toString();
	}
	
	
}
