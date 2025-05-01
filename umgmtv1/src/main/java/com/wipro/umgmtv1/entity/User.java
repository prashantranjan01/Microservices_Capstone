package com.wipro.umgmtv1.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    String id;

    @Column(name = "firstname")
    String firstname;

    @Column(name = "lastname")
    String lastname;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "email", updatable = false, nullable = false, unique = true)
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "salt")
    String salt;

    @Column(name = "role_id")
    int roleId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
