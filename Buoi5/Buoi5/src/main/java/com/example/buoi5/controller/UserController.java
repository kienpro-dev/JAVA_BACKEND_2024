package com.example.buoi5.controller;

import com.example.buoi5.exception.NotFoundException;
import com.example.buoi5.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class UserController {
    private List<User> users;

    @PostConstruct
    public void init() {
        users = IntStream.range(0, 10)
                .mapToObj(i -> new User(i, "username " + i, "pass " + i))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/users/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        for(User u : users) {
            if(u.getId() == id) {
                return ResponseEntity.ok().body(u);
            }
        }
        throw new NotFoundException("Khong tim thay user id: " + id);
    }

    @PostMapping("users/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        users.add(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("users/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        users.remove(id);
        return ResponseEntity.ok().body(users);
    }
}
