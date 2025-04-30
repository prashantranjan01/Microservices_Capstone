package com.wipro.umgmtv1.service;

import com.wipro.umgmtv1.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wipro.umgmtv1.dto.UserData;
import com.wipro.umgmtv1.entity.User;

public interface UserService {
	
	void register(User user);

	UserData login(String userName,String password);

	User findByUsername(String username) throws UserNotFoundException;
}
