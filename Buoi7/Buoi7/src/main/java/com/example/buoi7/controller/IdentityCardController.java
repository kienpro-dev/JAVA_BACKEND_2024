package com.example.buoi7.controller;

import com.example.buoi7.dto.IdentityCardDto;
import com.example.buoi7.dto.UserDto;
import com.example.buoi7.entity.IdentityCard;
import com.example.buoi7.entity.User;
import com.example.buoi7.repository.IdentityCardRepository;
import com.example.buoi7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentityCardController {
    @Autowired
    private IdentityCardRepository identityCardRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ics")
    public ResponseEntity<?> findAllIdentityCard() {
        return ResponseEntity.ok().body(identityCardRepository.findAll());
    }

    @PostMapping("/ics")
    public ResponseEntity<?> createIdentityCard(@RequestBody IdentityCardDto
                                                            identityCardDto) {
        IdentityCard ic = new IdentityCard();
        ic.setCode(identityCardDto.getCode());

        User userFind = userRepository.findById(identityCardDto.getUserId())
                        .orElseThrow(() -> {
                            throw new RuntimeException("Not found user id: "
                                    + identityCardDto.getUserId());
                        });
        ic.setUser(userFind);
        return ResponseEntity.ok().body(identityCardRepository.save(ic));
    }
}
