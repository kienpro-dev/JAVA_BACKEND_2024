package com.example.buoi9.controller;

import com.example.buoi9.Service.AccountService;
import com.example.buoi9.dto.AccountDto;
import com.example.buoi9.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(accountService.getAll());
    }

    @PostMapping("/accounts/create-save-local")
    public ResponseEntity<?> create(@ModelAttribute AccountDto accountDto) throws IOException {
        return ResponseEntity.ok().body(accountService.createUsingLocalDisk(accountDto));
    }

    @PostMapping("/accounts/create-save-cloudinary")
    public ResponseEntity<?> create2(@ModelAttribute AccountDto accountDto) throws IOException {
        return ResponseEntity.ok().body(accountService.createUsingCloudinary(accountDto));
    }
}
