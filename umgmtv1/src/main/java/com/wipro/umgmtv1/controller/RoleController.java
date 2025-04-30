package com.wipro.umgmtv1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.umgmtv1.entity.RoleMenu;
import com.wipro.umgmtv1.service.RoleMenuService;

@RestController
@RequestMapping("/rolemenu")
public class RoleController {

	@Autowired
	RoleMenuService roleMenuService;

	@GetMapping("/{id}")
	List<RoleMenu> findByUserId(@PathVariable int id)
	{

		return roleMenuService.findByRoleId(id);

	}


}
