package com.perficient.hr.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExceptionHandlingUtil {
	
	private ExceptionHandlingUtil(){
		
	}
	
	public static WsError returnErrorObject(String logText, Exception exception) {
		return new WsError(logText +" Exception is: "+exception.getLocalizedMessage());
	}
	
	public static WsError returnErrorObject(String logText, Exception exception, Integer errorCode) {
		return new WsError(logText +" Exception is: "+exception.getLocalizedMessage(), errorCode);
	}
	
	public static WsError returnErrorObject(String logText, Integer errorCode) { 
		return new WsError(logText , errorCode);
	}
	
	public static void closeSession(Session session) {
		if(session != null && session.isOpen()) {
			session.close();
		}
	}
	
	public static void transactionRollback(Transaction transaction) {
		if(transaction != null && transaction.isActive()) {
			transaction.rollback();
		}
	}
	
	public static void closeSessionOnError(Session session, Transaction transaction) {
		if(session != null && session.isOpen()) {
			if(transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			session.close();
		}
	}
}