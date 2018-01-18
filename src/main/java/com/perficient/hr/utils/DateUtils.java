package com.perficient.hr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static final String SQL_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final SimpleDateFormat MMddyyyy = new SimpleDateFormat("MM/dd/yyyy");
	
	private DateUtils(){
		
	}
	
	public static SimpleDateFormat getiCalDateFormat(){
		return new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
	}
	
	public static Date getDateByddMMMyyyy(String date) throws ParseException{
		SimpleDateFormat ddMMMyyyyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		return ddMMMyyyyFormat.parse(date);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * SimpleDateFormat isn't thread safe. Don't declare it as static outside the method.
	 */
	public static Date getDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATE_FORMAT);
		return sdf.parse(date);
	}

	public static Date getDate(String date, int month) throws ParseException {
		int val = 0;
		if(date.contains("/")){
			val = Integer.valueOf(date.split("/")[0]);
		} else if(date.contains("-")){
			val = Integer.valueOf(date.split("-")[0]);
		}
		if(date.contains("-")){
			try {
				Date leavedt = new SimpleDateFormat("dd-MMM-yyyy").parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(leavedt);
				if((cal.get(Calendar.MONTH)+1) == month){
					return leavedt;
				} else {
					cal.set(cal.get(Calendar.YEAR), (month-1), (cal.get(Calendar.MONTH)+1));
					return cal.getTime();
				}
			} catch (ParseException e) {
				if(val == month){
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
					return sdf.parse(date);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					return sdf.parse(date);
				}
			}
		} else {
			if(val == month){
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				return sdf.parse(date);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				return sdf.parse(date);
			}	
		}
	}
	
    public static String convertMilliSecondsToStringDate(String milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        SimpleDateFormat sdf = new SimpleDateFormat(SQL_DATE_FORMAT);
        return sdf.format(calendar.getTime());
    }
    
    public static Date convertMilliSecondsToDate(String milliSeconds){
        return new Date(Long.parseLong(milliSeconds));
    }
}
