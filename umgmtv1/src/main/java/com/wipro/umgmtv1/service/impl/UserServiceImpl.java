package com.wipro.umgmtv1.service.impl;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import com.wipro.umgmtv1.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.umgmtv1.dto.UserData;
import com.wipro.umgmtv1.entity.User;
import com.wipro.umgmtv1.repo.UserRepo;
import com.wipro.umgmtv1.service.UserService;
import com.wipro.umgmtv1.util.AppConstant;
 

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public void register(User user) {
		 
		String password= user.getPassword();		 
	 
		String salt=BCrypt.gensalt();		
        String hashedPassword = BCrypt.hashpw(password, salt);
        user.setSalt(salt);
        user.setPassword(hashedPassword);
        userRepo.save(user);
		
		
	}

	@Override
	public UserData login(String userName, String password) {
		
		User user= userRepo.findByUserId(userName);
		if(null!=user)
		{
			String salt=user.getSalt();
			String hashedPassword = BCrypt.hashpw(password, salt);
			if(hashedPassword.equals(user.getPassword()))
			{
				UserData userData= new UserData();
				userData.setUser(user);
			    String token= getJWTToken(userName);
				//String token="";
				userData.setToken(token);
				return userData;
			}
			
		}		
		return null;	
	 
	}

	@Override
	public User findByUsername(String username) throws UserNotFoundException {
		return this.userRepo.findByUserId(username);
	}

	private String getJWTToken(String username) {

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
 
		String token = Jwts
				.builder()
				.setId("wiproUserMgmt")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						AppConstant.JWT_SECRET.getBytes()).compact();

		return token;
	}
}
