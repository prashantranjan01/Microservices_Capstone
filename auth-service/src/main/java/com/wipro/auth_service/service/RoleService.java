package com.wipro.auth_service.service;

public interface RoleService {
    boolean hasPermission(String action);
//    void changeRole(String username, RoleChangeRequest roleId) throws UserNotFoundException;
}
