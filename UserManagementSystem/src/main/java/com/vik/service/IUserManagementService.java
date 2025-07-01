package com.vik.service;

import java.util.List;

import com.vik.entity.MyUser;
import com.vik.model.LoginUser;

public interface IUserManagementService 
{

	public String signIn(MyUser user);
	public List<MyUser> getAllUsers();
	public String verifyLogin(LoginUser logUser);
	public MyUser getUserById(Integer id);
	public String removeUserById(Integer id);
	String updateUser(MyUser user);
	String removeAllUsers();
}
