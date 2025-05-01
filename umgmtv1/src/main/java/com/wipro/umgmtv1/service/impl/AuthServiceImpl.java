package com.wipro.umgmtv1.service.impl;

import com.wipro.umgmtv1.dto.AuthData;
import com.wipro.umgmtv1.dto.UserData;
import com.wipro.umgmtv1.entity.User;
import com.wipro.umgmtv1.exception.InvalidPasswordException;
import com.wipro.umgmtv1.exception.UserNotFoundException;
import com.wipro.umgmtv1.repo.UserRepo;
import com.wipro.umgmtv1.service.AuthService;
import com.wipro.umgmtv1.util.AppConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepo userRepo;

    @Override
    public AuthData register(User user) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setSalt(salt);
        user.setId(UUID.randomUUID().toString());
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User createdUser = userRepo.save(user);
        UserData userData = new UserData(createdUser);
        String token = getJWTToken(userData.getId());
        return new AuthData(userData, token);
    }

    @Override
    public AuthData login(String username, String password) throws InvalidPasswordException, UserNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User with username " + username + " not found.");
        }
        String salt = user.getSalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        if (!hashedPassword.equals(user.getPassword())) {
            throw new InvalidPasswordException();
        }
        UserData userData = new UserData(user);
        String token = getJWTToken(userData.getId());
        return new AuthData(userData, token);
    }

    @Override
    public UserData findByUsername(String username) throws UserNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User with username : " + username + " not found.");
        }
        return new UserData(user);
    }

    @Override
    public UserData updateByUsername(String username, UserData updatedUser) throws UserNotFoundException {

        User existinguser=userRepo.findByUsername(username);
        if(existinguser==null){
            throw new UserNotFoundException("User with username: "+username+" not found");
        }
        if (updatedUser.getFirstname() != null) {
            existinguser.setFirstname(updatedUser.getFirstname());
        }
        if(updatedUser.getLastname()!=null){
            existinguser.setLastname(updatedUser.getLastname());
        }
        existinguser.setUpdatedAt(LocalDateTime.now());
        User savedUser= userRepo.save(existinguser);
        return new UserData(savedUser);
    }

    @Override
    public void changePassword(String username, String currentPassword, String UpdatedPassword) throws UserNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User with username : " + username + " not found.");
        }

        String storedSalt = user.getSalt();
        String storedHash = user.getPassword();

        String inputHash = BCrypt.hashpw(currentPassword, storedSalt);
        if (!inputHash.equals(storedHash)) {
            throw new InvalidPasswordException();
        }
        String newSalt=BCrypt.gensalt();
        String newHashedPassword=BCrypt.hashpw(UpdatedPassword,newSalt);
        user.setSalt(newSalt);
        user.setPassword(newHashedPassword);
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
    }

    @Override
    public void deleteByUserName(String username) throws UserNotFoundException {
        User existinguser = userRepo.findByUsername(username);
        if (existinguser == null) {
            throw new UserNotFoundException("User with username : " + username + " not found.");
        }
        userRepo.delete(existinguser);
    }


    private String getJWTToken(String id) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts
                .builder()
                .setId("auth-service")
                .setSubject(id)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstant.JWT_EXPIRATION_LIMIT))
                .signWith(SignatureAlgorithm.HS512,
                        AppConstant.JWT_SECRET.getBytes()).compact();
    }
}
