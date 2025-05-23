package com.wipro.auth_service.controller;

import com.wipro.auth_service.dto.APIResponse;
import com.wipro.auth_service.dto.AuthData;
import com.wipro.auth_service.entity.User;
import com.wipro.auth_service.repo.UserRepo;
import com.wipro.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService userService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/register")
    ResponseEntity<APIResponse<?>> register(
            @RequestBody User user) {
        try {
            AuthData authData = userService.register(user);
            APIResponse<AuthData> apiResponse = new APIResponse<>(HttpStatus.CREATED, "User registered successfully.", authData);
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e){
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.CONFLICT, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }
    }

    @PostMapping("/login")
    ResponseEntity<APIResponse<?>> login(
            @RequestParam String username,
            @RequestParam String password) {
        try {
            AuthData authData = userService.login(username, password);
            APIResponse<AuthData> apiResponse = new APIResponse<>(HttpStatus.OK, "User logged in successfully.", authData);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            APIResponse<?> apiResponse = new APIResponse<>(HttpStatus.UNAUTHORIZED, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }
    }
}
