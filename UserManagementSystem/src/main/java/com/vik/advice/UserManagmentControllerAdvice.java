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
		return new ResponseEntity<String>
					("Problem ::"+exp.getMessage(),HttpStatus.BAD_REQUEST);
		
	}
	
	// Method for All kind of Exception handling
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception e)
	{
		return new ResponseEntity<String>
					("Problem ::"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		 	
	}
}