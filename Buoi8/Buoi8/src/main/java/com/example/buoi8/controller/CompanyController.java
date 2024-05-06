package com.example.buoi8.controller;

import com.example.buoi8.dto.CompanyDto;
import com.example.buoi8.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/company")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(companyService.reads());
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(companyService.read(id));
    }

    @PostMapping("/company")
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok().body(companyService.create(companyDto));
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CompanyDto companyDto) throws Exception {
        return ResponseEntity.ok().body(companyService.update(id, companyDto));
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        companyService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully!");
    }
}
