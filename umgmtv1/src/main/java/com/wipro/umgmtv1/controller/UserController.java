package com.wipro.umgmtv1.controller;

import com.wipro.umgmtv1.exception.UserNotFoundException;
import com.wipro.umgmtv1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.umgmtv1.dto.UserData;
import com.wipro.umgmtv1.entity.User;
import com.wipro.umgmtv1.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepo userRepo;
	
	
	@PostMapping("/register")
	void Register(@RequestBody User user)
	{
		userService.register(user);
	}
	
	@PostMapping("/login")
	UserData login(@RequestParam String userName,@RequestParam String password)
	{
		return userService.login(userName,password);

	}


	@GetMapping
	public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
		try {
			User user = userService.findByUsername(username);
			return ResponseEntity.ok(user);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
}
