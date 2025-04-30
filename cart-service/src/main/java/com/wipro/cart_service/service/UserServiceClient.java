package com.wipro.cart_service.service;


import com.wipro.cart_service.dto.RoleMenuResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {

    @GetMapping("/rolemenu/{roleId}")
    List<RoleMenuResponse> getRoleMenus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable int roleId);

    @GetMapping("/user")
    ResponseEntity<?> getUserByUsername(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String username);
}