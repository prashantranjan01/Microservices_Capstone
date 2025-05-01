package com.wipro.umgmtv1.service;

import com.wipro.umgmtv1.dto.RoleChangeRequest;
import com.wipro.umgmtv1.entity.Role;
import com.wipro.umgmtv1.exception.UserNotFoundException;

public interface RoleService {
    boolean hasPermission(String action);
//    void changeRole(String username, RoleChangeRequest roleId) throws UserNotFoundException;
}
