package com.wipro.umgmtv1.service;

import java.util.List;

import com.wipro.umgmtv1.entity.RoleMenu;

public interface RoleMenuService {

	List<RoleMenu> findByRoleId(int id);
	boolean hasPermission(int roleId, String permissionCode);


}
