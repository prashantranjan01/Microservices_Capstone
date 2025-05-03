package com.wipro.auth_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(updatable = false, nullable = false)
    String id;

    @Column(name = "role_id")
    int roleId;

    @Column(name = "action")
    String action;

    @Column(name = "flag", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}