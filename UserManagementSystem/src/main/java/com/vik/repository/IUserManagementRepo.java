package com.vik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vik.entity.MyUser;

public interface IUserManagementRepo extends JpaRepository<MyUser, Integer> {

	public MyUser findByEmail(String email);
}
