package com.wipro.umgmtv1.dto;

import com.wipro.umgmtv1.entity.User;

public class UserData {
	
	User user;
	String token;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "UserData [user=" + user + ", token=" + token + "]";
	}
	
	

}
