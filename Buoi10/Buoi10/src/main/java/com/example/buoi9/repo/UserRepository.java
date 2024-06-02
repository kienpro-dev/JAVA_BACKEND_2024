package com.example.buoi9.repo;

import com.example.buoi9.model.User;
import com.example.buoi9.service.UserDetailImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);

    default User getUser(UserDetailImpl currentUser) {
        return findUserByUsername(currentUser.getUsername());
    }
}
