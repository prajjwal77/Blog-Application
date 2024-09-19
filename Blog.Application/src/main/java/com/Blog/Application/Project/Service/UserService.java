package com.Blog.Application.Project.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Blog.Application.Project.Model.User;
import com.Blog.Application.Project.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private  UserRepository userRepository;
	
	 @Autowired
	  private BCryptPasswordEncoder passwordEncoder;

	 
	 public Optional<User> getUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }
	 
	 public void saveUser(User user) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepository.save(user);
	    }
}

