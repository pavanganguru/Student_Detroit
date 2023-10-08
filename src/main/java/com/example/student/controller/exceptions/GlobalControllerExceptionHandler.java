package com.example.student.controller.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.student.entity.ErrorMessage;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        new Date(),
		        ex.getMessage(),
		        request.getDescription(false));
		    
		    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadFormatException.class)
	public ResponseEntity<ErrorMessage> badFormat(BadFormatException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), new Date(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(RequiredFieldException.class)
	public ResponseEntity<ErrorMessage> requiredField(RequiredFieldException ex, WebRequest request){
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        new Date(),
		        ex.getMessage(),
		        request.getDescription(false));
		    
		    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

}
