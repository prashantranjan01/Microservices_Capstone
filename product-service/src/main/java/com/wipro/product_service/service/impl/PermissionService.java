package com.wipro.product_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.product_service.dto.RoleMenuResponse;
import com.wipro.product_service.dto.UserResponse;
import com.wipro.product_service.service.UserServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final UserServiceClient userServiceClient;

    public PermissionService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public boolean hasPermission(String authHeader, String requiredPermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ResponseEntity<?> user =userServiceClient.getUserByUsername(authHeader , authentication.getName());
        if(user.getStatusCode() != HttpStatus.OK){
            throw new UsernameNotFoundException("User not found");
        }
        ObjectMapper mapper = new ObjectMapper();
        UserResponse userResponse = mapper.convertValue(user.getBody(), UserResponse.class);

        List<RoleMenuResponse> roleMenus = userServiceClient.getRoleMenus(authHeader, userResponse.getRoleId());

        return roleMenus.stream()
                .anyMatch(menu ->
                        menu.getMenuName().equalsIgnoreCase(requiredPermission)
                                && "Y".equalsIgnoreCase(menu.getShowMenu())
                );
    }
}