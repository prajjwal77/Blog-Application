package com.Blog.Application.Project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Blog.Application.Project.Model.User;
import com.Blog.Application.Project.Service.UserService;

//@Controller
public class UserController {

//	@Autowired
//	private UserService userService;
//	
//	@GetMapping("/register")
//	public String regiter(Model model) {
//		model.addAttribute("user", new User());
//		return "register";
//	}
//	
//	public String registerUser(@ModelAttribute  User user) {
//		userService.saveUser(user);
//		return "redirect:/login";
//	}
//	
//	@GetMapping("login")
//	public String login() {
//		return "login";
//	}
}
