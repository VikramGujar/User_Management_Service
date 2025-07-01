package com.vik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vik.entity.MyUser;
import com.vik.model.UserPrinciple;
import com.vik.repository.IUserManagementRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserManagementRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		
		MyUser user = repo.findByEmail(email);
		
		if(user == null)
		{
			throw new UsernameNotFoundException("User not found");
		}
		
		return new UserPrinciple(user);
	}

}
