package com.example.buoi6;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where " +
            "u.id = ?1 and u.username = ?2")
    User findUser(Integer id, String username);
}
