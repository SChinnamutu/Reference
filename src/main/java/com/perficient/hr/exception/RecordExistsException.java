package com.perficient.hr.exception;

@SuppressWarnings("serial")
public class RecordExistsException extends GenericException{
	public RecordExistsException() {
		super();
	}
	
	public RecordExistsException(String msg) {
		super(msg);
	}
	
	public RecordExistsException(Throwable ex) {
		super(ex);
	}
	
	public RecordExistsException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
