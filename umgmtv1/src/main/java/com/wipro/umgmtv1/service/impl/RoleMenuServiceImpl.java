package com.wipro.umgmtv1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.umgmtv1.entity.RoleMenu;
import com.wipro.umgmtv1.repo.RoleRepo;
import com.wipro.umgmtv1.service.RoleMenuService;
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	@Autowired
	RoleRepo roleMenuRepo;
	
	@Override
	public List<RoleMenu> findByRoleId(int id) {

		return roleMenuRepo.findByRoleId(id);
	}

	@Override
	public boolean hasPermission(int roleId, String permissionCode) {
		return roleMenuRepo.existsByRoleIdAndMenuNameAndShowMenu(roleId, permissionCode, "Y");
	}



}
