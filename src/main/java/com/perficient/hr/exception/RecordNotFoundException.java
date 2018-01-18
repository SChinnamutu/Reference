package com.perficient.hr.exception;

@SuppressWarnings("serial")
public class RecordNotFoundException extends GenericException{

	public RecordNotFoundException() {
		super();
	}
	
	public RecordNotFoundException(String msg) {
		super(msg);
	}
	
	public RecordNotFoundException(Throwable ex) {
		super(ex);
	}
	
	public RecordNotFoundException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
