package com.wipro.auth_service.controller;

import com.wipro.auth_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    //create role - post

    //delete role - delete {id}

    //change flag - put change flag
}
