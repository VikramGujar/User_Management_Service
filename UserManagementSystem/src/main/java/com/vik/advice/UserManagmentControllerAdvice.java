package com.vik.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// This Class is for Global Exception handling 


@RestControllerAdvice
public class UserManagmentControllerAdvice 
{
	// Method to handle IllegalArgumentException exception
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleArgumentException(IllegalArgumentException exp)
	{
		ResponseEntity<String> res = new 
				ResponseEntity<String>("Problem ::"+exp.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return res;
	}
	
	// Method for All kind of Exception handling
	public ResponseEntity<String> handleAllExceptions(Exception e)
	{
		ResponseEntity<String> res = new ResponseEntity<String>("Problem ::"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return res;	
	}
}