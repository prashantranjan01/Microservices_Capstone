package com.wipro.umgmtv1.controller;

import com.wipro.umgmtv1.dto.APIResponse;
import com.wipro.umgmtv1.dto.ResetPasswordAPIRequest;
import com.wipro.umgmtv1.dto.UserData;
import com.wipro.umgmtv1.exception.UserNotFoundException;
import com.wipro.umgmtv1.service.AuthService;
import com.wipro.umgmtv1.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            APIResponse<?> userNotAPIResponse = new APIResponse<>(HttpStatus.NOT_FOUND, null, "User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotAPIResponse);
        }
    }

    //update user - fname , lname

    @PutMapping
    public ResponseEntity<APIResponse<?>> updateUser(@RequestParam String username,
            @RequestBody UserData updatedUserData){
        try{
            UserData updatedUser = userService.updateByUsername(username, updatedUserData);
            APIResponse<UserData> response = new APIResponse<>(HttpStatus.OK, updatedUserData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            APIResponse<?> userNotAPIResponse = new APIResponse<>(HttpStatus.BAD_REQUEST,e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userNotAPIResponse);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<APIResponse<?>> changePassword(@RequestParam String username,
                                                         @RequestBody ResetPasswordAPIRequest changePasswordRequest){
        try{
            userService.changePassword(username,changePasswordRequest.getCurrentPassword(),changePasswordRequest.getNewPassword());
            APIResponse<String> response=new APIResponse<>(HttpStatus.OK,"Password changed successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse<>(HttpStatus.BAD_REQUEST,e.getMessage(),null));
        }
    }

    @DeleteMapping
    public ResponseEntity<APIResponse<?>> deleteUser(@RequestParam String username){
        try{
            userService.deleteByUserName(username);
            APIResponse<String> response=new APIResponse<>(HttpStatus.OK,"User deleted successfully.");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            APIResponse<?> notFound=new APIResponse<>(HttpStatus.BAD_REQUEST,e.getMessage(),null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notFound);
        }
    }

    @GetMapping("/check")
    public boolean hasPermission(@RequestParam String action) {
        return roleService.hasPermission(action);
    }
}
