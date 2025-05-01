package com.wipro.umgmtv1.service.impl;

import com.wipro.umgmtv1.entity.Role;
import com.wipro.umgmtv1.entity.User;
import com.wipro.umgmtv1.exception.UserNotFoundException;
import com.wipro.umgmtv1.repo.RoleRepo;
import com.wipro.umgmtv1.repo.UserRepo;
import com.wipro.umgmtv1.service.RoleService;
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
            Role role = roles.get(0);
            return role.getFlag().equals("Y");
        }
        return false;
    }
}
