package com.wipro.auth_service.service;

import com.wipro.auth_service.dto.AuthData;
import com.wipro.auth_service.dto.UserData;
import com.wipro.auth_service.entity.User;
import com.wipro.auth_service.exception.InvalidPasswordException;
import com.wipro.auth_service.exception.UserNotFoundException;

public interface AuthService {

    AuthData register(User user);

    AuthData login(String userName, String password) throws InvalidPasswordException, UserNotFoundException;

    UserData findByUsername(String username) throws UserNotFoundException;

    UserData findById(String id) throws UserNotFoundException;

    UserData updateByUsername(String username,UserData updatedUser) throws UserNotFoundException;

    void changePassword(String username,String currentPassword,String UpdatedPassword) throws UserNotFoundException;

    void deleteByUserName(String username) throws UserNotFoundException;
}
