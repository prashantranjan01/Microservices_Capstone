package com.wipro.auth_service.service.impl;

import com.wipro.auth_service.entity.Role;
import com.wipro.auth_service.entity.User;
import com.wipro.auth_service.exception.UserNotFoundException;
import com.wipro.auth_service.repo.RoleRepo;
import com.wipro.auth_service.repo.UserRepo;
import com.wipro.auth_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public boolean hasPermission(String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        List<Role> roles = roleRepo.findByRoleIdAndAction(user.getRoleId(), action);
        if (null != roles && !roles.isEmpty()) {
            Role role = roles.getFirst();
            return role.getFlag().equals("Y");
        }
        return false;
    }
}
