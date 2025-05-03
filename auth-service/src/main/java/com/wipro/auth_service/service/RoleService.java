package com.wipro.auth_service.service;

public interface RoleService {
    boolean hasPermission(String action);
}
