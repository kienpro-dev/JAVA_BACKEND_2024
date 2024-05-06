package com.example.buoi7.controller;

import com.example.buoi7.dto.UserDto;
import com.example.buoi7.entity.User;
import com.example.buoi7.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
//    @Autowired
//    private UserRepository userRepository;

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<?> findAllUser(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size) {
        if(page == null) {
            return ResponseEntity.ok().body(userRepository.findAll());
        }
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok().body(users.getContent());
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
