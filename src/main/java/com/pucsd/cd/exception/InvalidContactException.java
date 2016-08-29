package com.pucsd.cd.exception;
public class InvalidContactException extends Exception {
	
	public InvalidContactException() {
		super("Invalid contact");
	}
	
	public InvalidContactException(String message) {
		super(message);
	}
	
}
