package com.vik.repository;

// Implementation interface for In memory proxy class 

import org.springframework.data.jpa.repository.JpaRepository;

import com.vik.entity.MyUser;

// Extends JpaRepository to provide all methods implementation in proxy class 

public interface IUserManagementRepo extends JpaRepository<MyUser, Integer> {

	// For user authentication by Email
	public MyUser findByEmail(String email);
}
