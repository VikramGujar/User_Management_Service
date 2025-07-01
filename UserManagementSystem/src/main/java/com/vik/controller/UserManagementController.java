package com.vik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vik.entity.MyUser;
import com.vik.model.LoginUser;
import com.vik.service.IUserManagementService;

@RestController
public class UserManagementController 
{

	@Autowired
	private IUserManagementService ser;
	
	@GetMapping("/home")
	public ResponseEntity<String> getHome()
	{
		ResponseEntity<String> res = new ResponseEntity<String>("Welcome To Spring Boot",HttpStatus.OK);
		return res;
	}
	

	@PostMapping("/register")
	public ResponseEntity<String> addUser(@RequestBody MyUser user)
	{
		String msg = ser.signIn(user);
		ResponseEntity<String> response = new ResponseEntity<String>(msg,HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> checkUser(@RequestBody LoginUser log)
	{
		String msg = ser.verifyLogin(log);
		ResponseEntity<String> response = new ResponseEntity<String>(msg,HttpStatus.OK);
		return response;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/allUsers")
	public ResponseEntity<List<MyUser>> showAllUsers()
	{
		List<MyUser> allUsers = ser.getAllUsers();
		ResponseEntity<List<MyUser>> response = 
				new ResponseEntity<List<MyUser>>(allUsers,HttpStatus.OK);
		return response;
	}
	

	@GetMapping("/getByid/{id}")
	public ResponseEntity<MyUser> showById(@PathVariable Integer id)
	{
		MyUser userById = ser.getUserById(id);
		ResponseEntity<MyUser> response = new ResponseEntity<MyUser>(userById,HttpStatus.OK);
		return response;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id)
	{
		String msg = ser.removeUserById(id);
		ResponseEntity<String> res = new ResponseEntity<String>(msg,HttpStatus.OK);
		return res;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteAll")
	public ResponseEntity<String> deleteAllUsers()
	{
		String msg = ser.removeAllUsers();
		ResponseEntity<String> res = new ResponseEntity<String>(msg,HttpStatus.OK);
		return res;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateUser")
	public ResponseEntity<String> userUpdate(@RequestBody MyUser user)
	{
		String msg = ser.updateUser(user);
		ResponseEntity<String> res = new ResponseEntity<String>(msg,HttpStatus.OK);
		return res;
	}
	
	
}
