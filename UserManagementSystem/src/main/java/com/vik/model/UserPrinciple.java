package com.vik.model;

// To create implementation object of UserDetails class 

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vik.entity.MyUser;

public class UserPrinciple implements UserDetails 
{

	private MyUser user;
	
	
	
	public UserPrinciple(MyUser user) 
	{
		this.user = user;
	}

	// To check Role Authority
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return List.of(new SimpleGrantedAuthority(user.getRole()));
	}

	@Override
	public String getPassword() 
	{
		return user.getPassword();
	}

	@Override
	public String getUsername() 
	{
		return user.getEmail();
	}
	
}
