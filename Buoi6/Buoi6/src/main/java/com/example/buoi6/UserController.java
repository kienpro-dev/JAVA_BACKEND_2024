package com.example.buoi6;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // @Autowired
    // private UserRepository userRepository;

    @GetMapping("/user")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/user/{id}/{username}")
    public ResponseEntity<?> findByIdAndUsername(@PathVariable int id, @PathVariable String username) {
        return ResponseEntity.ok().body(userRepository.findUser(id, username));
    }

    // cùng endpoint, khác http method
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User userCreate = userRepository.save(user);
        return ResponseEntity.ok().body(userCreate);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        Optional<User> currentUser = userRepository.findById(id);
        currentUser.get().setUsername(user.getUsername());
        currentUser.get().setPassword(user.getPassword());
        userRepository.save(currentUser.get());
        return ResponseEntity.ok().body(currentUser);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<?> updateUser2(@PathVariable int id, @RequestBody User user) {
        Optional<User> currentUser = userRepository.findById(id);
        if (user.getUsername() != null) {
            currentUser.get().setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            currentUser.get().setPassword(user.getPassword());
        }
        userRepository.save(currentUser.get());
        return ResponseEntity.ok().body(currentUser);
    }
}
