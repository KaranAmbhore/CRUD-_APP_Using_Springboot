package com.crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.entities.User;
import com.crud.service.UserService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("api/user/")
public class UserController {

	@Autowired
	private User user;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String homePage() {
		return "home";
	}

	@GetMapping("register")
	public Object addUser() {

		return "register";
	}

	@PostMapping("registeruser")
	public Object registerUser(@RequestParam int userid, @RequestParam String username, @RequestParam String email,
			@RequestParam String password, Model model) {

//		System.out.println(userid);
//		System.out.println(username);
//		System.out.println(email);
//		System.out.println(password);

		model.addAttribute("id", userid);
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);

		user.setId(userid);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);

		return userService.addUser(user, model);

	}

	@GetMapping("login")
	public String loginPage() {

		return "login";
	}

	@PostMapping("loginuser")
	public Object loginHandler(@RequestParam String username, @RequestParam String password, Model model) {

		model.addAttribute("username", username);
		model.addAttribute("password", password);

		System.out.println(username);
		System.out.println(password);

		return userService.loginUser(username, password, model);
	}

	@GetMapping("getall")
	public String userList(Model model) {

		List<User> users = userService.getAllUsers();

		model.addAttribute("users", users);

		return "allusers";
	}

	@GetMapping("search")
	public String searchNbyId() {

		return "getuser";
	}

	@PostMapping("searchbyid")
	public String getById(@RequestParam int id, Model model) {

		Optional<User> optUser = userService.getUserById(id, model);
		if (optUser != null) {

			User user = optUser.get();

			model.addAttribute("user", user);
			return "userbyid";
		} else {
			model.addAttribute("error", "User Not Found");
			return "userbyid";
		}

	}

	@PostMapping("searchbyname")
	public String getByName(@RequestParam String name, Model model) {

		System.out.println(name);
		model.addAttribute("name", name);

		User user = userService.getUserByIdOrName(name, model);

		if (user != null) {
			model.addAttribute("user", user);
			return "userbyid";
		} else {
			model.addAttribute("error", "User Not Found");
			return "userbyid";
		}

	}

	@PostMapping("deleteuser")
	public String deleteByUserId(@RequestParam int id, Model model) {

		userService.deleteByUserId(id, model);

		userList(model);
		
		return "allusers";

//		return null;
	}

	@PostMapping("usrlogin")
	public Object loginUser(@RequestParam(required = false) String username,
			@RequestParam(required = false) String email, @RequestParam String password, Model model) {

		model.addAttribute("username", username);
		model.addAttribute("password", password);

		return userService.loginUser(username, email, password, model);
	}

	
	@PostMapping("searchname")
	public String searchByName(@RequestParam String name , Model model){
		
		model.addAttribute("name", name);

		List<User> users = userService.searchByName(name, model);
		
		if(!users.isEmpty()) {
			
			model.addAttribute("users", users);
			
			return "getuser";
		}else {
			model.addAttribute("error", "User Not Found");
			return "getuser";
		}
		
	}
	
	
	@GetMapping("update")
	public String updateUser() {
		
		return "updateuser";
	}
	
	@PostMapping("edit")
	public String updateUser(
			@RequestParam int userid,
			@RequestParam String username,
			@RequestParam String email,
			@RequestParam String password,
			Model model
			) {
	model.addAttribute("id", userid);
	model.addAttribute("username", username);
	model.addAttribute("email", email);
	model.addAttribute("password", password);
	;
			user.setId(userid);
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
		
		
		userService.updateUser(user,model);
		
		return "updateuser";
	}
	
	
	
//	@PostMapping("deletebyname")
//	@Transactional
//	public String deleteUserByName(@RequestParam String username,Model model) {
//		
//		userService.deleteUserByName(username,model);
//		
//		return "allusers";
//	}

}
