package com.perficient.hr.utils;

public class WsError {
	
	private String errorMessage;
	private Object exceptionObj;
	private Integer errorCode;
	
	public WsError(String errorMessage, Object exceptionObj, Integer errorCode) {
		this.errorMessage = errorMessage;
		this.exceptionObj = exceptionObj;
		this.errorCode = errorCode;
	}
	
	public WsError(String errorMessage, Object exceptionObj) {
		this.errorMessage = errorMessage;
		this.exceptionObj = exceptionObj;
	}

	public WsError(String errorMessage, Integer errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	
	public WsError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public Object getExceptionObj() {
		return exceptionObj;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}
	
}
