package com.crud.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.crud.dao.UserRepository;
import com.crud.entities.User;
import com.crud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Object addUser(User user, Model model) {

		Optional<User> optUser = userRepository.findById(user.getId());
		if (optUser.isPresent()) {

			model.addAttribute("error", "User with id " + user.getId() + " is already present.");
			return "register";
		}

		if (user.getId() != 0 && user.getId() > 0) {
			userRepository.save(user);
			model.addAttribute("success", "Registration successful.");
			return "login";
		} else {

			model.addAttribute("error", "User Id cannot be zero or null...");
			return "register";
		}

	}

	@Override
	public Object loginUser(String username, String password, Model model) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.getPassword().equals(password)) {

			return "loginsuccess";
		} else {

			model.addAttribute("error", "Incorrect Username or Password");
			return "login";
		}

	}

	@Override
	public List<User> getAllUsers() {

		List<User> users = userRepository.findAll();

		return users;
	}

//	@Override
//	public Optional<User> getUserById(int id, Model model) {
//
//		Optional<User> optUser = userRepository.findById(id);
//
//		if (optUser.isPresent()) {
//
//			return optUser;
//		} else {
//			return null;
//
//		}
//
//	}

	@Override
	public User getUserByIdOrName(String name, Model model) {

		User user = userRepository.findByUsername(name);
		if (user != null) {

			return user;
		}

		return null;
	}

	@Override
	public String deleteByUserId(int id, Model model) {

		Optional<User> optUser = userRepository.findById(id);

		if (id == 0 || id < 0) {

			model.addAttribute("error", "Invalid ID");
			return "allusers";
		}

		if (optUser.isPresent()) {

			userRepository.deleteById(id);
			model.addAttribute("message", "User deleted successfully.");
			return "allusers";
//			return null;

		} else {

			model.addAttribute("error", "User Not Found");
			return "allusers";

		}

	}

	@Override
	public Optional<User> getUserById(int id, Model model) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	public Object loginUser(String username, String email, String password, Model model) {

		User user = userRepository.findByUsername(username);
		User user2 = userRepository.findByEmail(email);
		if (username != null && user.getPassword().equals(password)) {

			model.addAttribute("message", "Login successful..");
			return "loginsuccess";

		} else if (email != null && user2.getPassword().equals(password)) {

			model.addAttribute("message", "Login successful..");
			return "loginsuccess";

		} else {

			model.addAttribute("error", "Invalid Username or password");
			return "login";
		}

	}

	public List<User> searchByName(String name, Model model) {

		List<User> users = userRepository.findAllByUsernameContaining(name);

//		User user = userRepository.findByUsername(name);
		if (users != null) {

			return users;
		}

		return null;
	}

	@Override
	public Object updateUser(User user, Model model) {

		System.out.println(user);
		Optional<User> optUser = userRepository.findById(user.getId());

		System.out.println(optUser);
		if (optUser.isPresent()) {
			User existingUser = optUser.get();
//			existingUser.setId(user.getId());
			existingUser.setUsername(user.getUsername());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(user.getPassword());
			userRepository.save(existingUser);
			
			model.addAttribute("message", "User Updated Successfully..");
			return "updateuser";

		}else {
			model.addAttribute("error", "User with id "+user.getId()+" is not found.");
			return "updateuser";
		}
	}

//	@Override
//	public Object deleteUserByName(String username, Model model) {
//
//		User user = userRepository.deleteByUsername(username);
//
//		if (user != null) {
//
//			model.addAttribute("message", "User deleted successfully..");
//			return "allusers";
//		} else {
//
//			model.addAttribute("error", "User Not Found");
//			return "allusers";
//		}
//
//	}

}
