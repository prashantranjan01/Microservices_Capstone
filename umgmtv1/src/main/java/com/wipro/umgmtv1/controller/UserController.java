package com.wipro.umgmtv1.controller;

import com.wipro.umgmtv1.dto.APIResponse;
import com.wipro.umgmtv1.dto.UserData;
import com.wipro.umgmtv1.exception.UserNotFoundException;
import com.wipro.umgmtv1.service.AuthService;
import com.wipro.umgmtv1.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AuthService userService;

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<APIResponse<?>> getUserByUsername(@RequestParam String username) {
        try {
            UserData user = userService.findByUsername(username);
            APIResponse<UserData> userAPIResponse = new APIResponse<>(HttpStatus.OK, user);
            return ResponseEntity.ok(userAPIResponse);
        } catch (UserNotFoundException e) {
            APIResponse<?> userNotAPIResponse = new APIResponse<>(HttpStatus.NOT_FOUND, "User not found.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotAPIResponse);
        }
    }

    //update user - fname , lname

    //change password - new password

    //change role - roleId

    //deleteUser - id


    @GetMapping("/check")
    public boolean hasPermission(@RequestParam String action) {
        return roleService.hasPermission(action);
    }
}
