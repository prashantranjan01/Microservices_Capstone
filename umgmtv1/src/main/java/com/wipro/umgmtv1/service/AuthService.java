package com.wipro.umgmtv1.service;

import com.wipro.umgmtv1.dto.AuthData;
import com.wipro.umgmtv1.dto.UserData;
import com.wipro.umgmtv1.entity.User;
import com.wipro.umgmtv1.exception.InvalidPasswordException;
import com.wipro.umgmtv1.exception.UserNotFoundException;

public interface AuthService {

    AuthData register(User user);

    AuthData login(String userName, String password) throws InvalidPasswordException, UserNotFoundException;

    UserData findByUsername(String username) throws UserNotFoundException;

    UserData updateByUsername(String username,UserData updatedUser) throws UserNotFoundException;

    void changePassword(String username,String currentPassword,String UpdatedPassword) throws UserNotFoundException;

    void deleteByUserName(String username) throws UserNotFoundException;
}
