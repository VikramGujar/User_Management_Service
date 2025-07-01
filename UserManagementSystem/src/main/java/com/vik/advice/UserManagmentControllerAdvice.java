package com.vik.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserManagmentControllerAdvice 
{

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleArgumentException(IllegalArgumentException exp)
	{
		ResponseEntity<String> res = new ResponseEntity<String>("Problem ::"+exp.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return res;
	}
	
	public ResponseEntity<String> handleAllExceptions(Exception e)
	{
		ResponseEntity<String> res = new ResponseEntity<String>("Problem ::"+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return res;	
	}
}