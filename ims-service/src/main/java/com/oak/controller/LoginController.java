package com.oak.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oak.dto.UserDto;
import com.oak.service.UserService;

@RestController
public class LoginController {
	private final Logger logger;
	private final UserService userService;

	@Autowired
	public LoginController(Logger logger, UserService userService) {
		this.logger = logger;
		this.userService = userService;
	}

	@PostMapping(value = "/login")
	public UserDto success(@RequestBody User user) {
		logger.info("Successfully authenticated User [ username: {} ]", user.getUsername());
		return userService.readByUsername(user.getUsername());
	}
	
	private static class User {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		@SuppressWarnings("unused")
		public void setUsername(String username) {
			this.username = username;
		}

		@SuppressWarnings("unused")
		public String getPassword() {
			return password;
		}

		@SuppressWarnings("unused")
		public void setPassword(String password) {
			this.password = password;
		}
	}
}
