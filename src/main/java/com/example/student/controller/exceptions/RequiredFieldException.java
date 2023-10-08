package com.example.student.controller.exceptions;

public class RequiredFieldException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequiredFieldException(String message) {
		super(message);
	}

}
