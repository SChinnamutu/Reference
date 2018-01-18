package com.perficient.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends Exception{

	public GenericException() {
		super();
	}
	
	public GenericException(String msg) {
		super(msg);
	}
	
	public GenericException(Throwable ex) {
		super(ex);
	}
	
	public GenericException(String msg, Throwable ex) {
		super(msg, ex);
	}
	
}
