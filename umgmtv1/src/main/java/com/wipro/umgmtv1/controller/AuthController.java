package com.wipro.umgmtv1.controller;

import com.wipro.umgmtv1.dto.APIResponse;
import com.wipro.umgmtv1.dto.AuthData;
import com.wipro.umgmtv1.entity.User;
import com.wipro.umgmtv1.exception.InvalidPasswordException;
import com.wipro.umgmtv1.exception.UserNotFoundException;
import com.wipro.umgmtv1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService userService;

    @PostMapping("/register")
    ResponseEntity<APIResponse<AuthData>> register(
            @RequestBody User user) {
        AuthData authData = userService.register(user);
        APIResponse<AuthData> apiResponse = new APIResponse<>(HttpStatus.CREATED, "User registered successfully.", authData);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    ResponseEntity<APIResponse<?>> login(
            @RequestParam String username,
            @RequestParam String password) {
        try{
            AuthData authData = userService.login(username, password);
            APIResponse<AuthData> apiResponse = new APIResponse<>(HttpStatus.OK, "User logged in successfully.", authData);
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e ){
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.UNAUTHORIZED , e.getMessage() , null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }
    }
}
