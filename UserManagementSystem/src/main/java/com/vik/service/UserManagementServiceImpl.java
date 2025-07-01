package com.vik.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vik.entity.MyUser;
import com.vik.model.LoginUser;
import com.vik.repository.IUserManagementRepo;

@Service
public class UserManagementServiceImpl implements IUserManagementService {

	@Autowired
	private IUserManagementRepo repo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtSer;
	

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public String signIn(MyUser user) 
	{
		user.setPassword(encoder.encode(user.getPassword()));
		MyUser savedUser = repo.save(user);
		return "User registered with "+savedUser.getId();
	}

	
	@Override
	public String verifyLogin(LoginUser logUser) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(logUser.getEmail(), logUser.getPassword()));
		
		if(authentication.isAuthenticated())
		{
			String token = jwtSer.generateToken(logUser.getEmail());
			return "User Logged in Successfully with Token : "+token;
		}
		throw new IllegalArgumentException("Check Login credentials, Login Failed!");
		 
	}

	
	@Override
	public List<MyUser> getAllUsers() 
	{
		List<MyUser> all = repo.findAll();
		return all;
	}


	@Override
	public MyUser getUserById(Integer id) 
	{
		Optional<MyUser> opt = repo.findById(id);
		if(opt.isPresent())
		{
			return opt.get();
		}
		throw new IllegalArgumentException("Record ID = "+id+" you want to retrive does not exist");
	}

	
	@Override
	public String removeUserById(Integer id) 
	{
		Optional<MyUser> optionalObj = repo.findById(id);
		
		if(optionalObj.isPresent())
		{
			repo.deleteById(id);
			return "User with "+id+" ID is deleted";
		}
		throw new IllegalArgumentException("Record ID = "+id+" You want to delete dose not exist");
	}

	
	
	@Override
	public String removeAllUsers() {
		repo.deleteAll();
		
		return "All records are deleted";
	}

	@Override
	public String updateUser(MyUser user) 
	{
		MyUser userData = repo.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Record ID = "+user.getId()+" you want to update does not exist"));
		
		BeanUtils.copyProperties(user, userData);
		
		repo.save(userData);
		
		return "Id "+user.getId()+" user updated";
	}



}
