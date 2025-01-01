package com.crud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);
	
	public User deleteByUsername(String username);
	
	public User findByEmail(String email);
	
	public List<User> findAllByUsernameContaining(String username);
	
}
