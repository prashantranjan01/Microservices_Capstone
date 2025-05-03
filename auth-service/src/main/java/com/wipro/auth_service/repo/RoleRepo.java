package com.wipro.auth_service.repo;

import com.wipro.auth_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, String> {
    List<Role> findByRoleIdAndAction(int roleId, String action);
}
