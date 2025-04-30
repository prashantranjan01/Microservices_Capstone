package com.wipro.umgmtv1.controller;



import com.wipro.umgmtv1.dto.RoleMenuResponse;
import com.wipro.umgmtv1.service.RoleMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rolemenu")
public class RoleMenuController {

    private final RoleMenuService roleMenuService;

    public RoleMenuController(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    // Existing endpoint
    @GetMapping("/{roleId}")
    public List<RoleMenuResponse> findByRoleId(@PathVariable int roleId) {
        return roleMenuService.findByRoleId(roleId).stream()
                .map(roleMenu -> {
                    RoleMenuResponse response = new RoleMenuResponse();
                    response.setMenuName(roleMenu.getMenuName());
                    response.setShowMenu(roleMenu.getShowMenu());
                    return response;
                })
                .collect(Collectors.toList());
    }

    // New endpoint for permission check
    @GetMapping("/has-permission")
    public boolean hasPermission(
            @RequestParam int roleId,
            @RequestParam String permissionCode) {
        return roleMenuService.hasPermission(roleId, permissionCode);
    }
}