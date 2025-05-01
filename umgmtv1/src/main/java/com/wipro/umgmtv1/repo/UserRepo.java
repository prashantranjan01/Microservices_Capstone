package com.wipro.umgmtv1.repo;

import com.wipro.umgmtv1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByUsername(String userName);
    void delete(User user);

}
