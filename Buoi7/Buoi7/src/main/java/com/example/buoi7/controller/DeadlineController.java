package com.example.buoi7.controller;

import com.example.buoi7.dto.DeadlineDto;
import com.example.buoi7.dto.IdentityCardDto;
import com.example.buoi7.entity.Deadline;
import com.example.buoi7.entity.IdentityCard;
import com.example.buoi7.entity.User;
import com.example.buoi7.repository.DeadlineRepository;
import com.example.buoi7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeadlineController {
    @Autowired
    private DeadlineRepository deadlineRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/deadlines")
    public ResponseEntity<?> findAllIdentityCard() {
        return ResponseEntity.ok().body(deadlineRepository.findAll());
    }

    @PostMapping("/deadlines")
    public ResponseEntity<?> createIdentityCard(@RequestBody DeadlineDto
                                                        deadlineDto) {
        Deadline dl = new Deadline();
        dl.setName(deadlineDto.getName());

        User userFind = userRepository.findById(deadlineDto.getUserId())
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found user id: "
                            + deadlineDto.getUserId());
                });
        dl.setUser(userFind);
        return ResponseEntity.ok().body(deadlineRepository.save(dl));
    }
}
