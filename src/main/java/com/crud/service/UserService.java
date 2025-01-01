package com.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;

import com.crud.entities.User;

public interface UserService {

	public Object addUser(User user,Model model);

	public Object loginUser(String username, String password, Model model);

	public List<User> getAllUsers();


	 public Optional<User> getUserById(int id, Model model);


	public User getUserByIdOrName(String name, Model model);


	public String deleteByUserId(int id, Model model);

	public Object loginUser(String username, String email, String password, Model model);

	public List<User> searchByName(String name, Model model);
//	public Object deleteUserByName(String username, Model model);

	public Object updateUser(User user, Model model);

}
