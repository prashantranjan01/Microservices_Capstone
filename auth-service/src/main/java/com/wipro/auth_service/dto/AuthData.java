package com.wipro.auth_service.dto;

public class AuthData {
    private UserData user;
    private String token;

    public AuthData(UserData user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
