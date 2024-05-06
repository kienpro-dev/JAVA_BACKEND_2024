package com.example.buoi8.controller;

import com.example.buoi8.dto.ShiftDto;
import com.example.buoi8.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShiftController {
    @Autowired
    private ShiftService shiftService;

    @GetMapping("/shift")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(shiftService.reads());
    }

    @GetMapping("/shift/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(shiftService.read(id));
    }

    @PostMapping("/shift")
    public ResponseEntity<?> create(@RequestBody ShiftDto shiftDto) throws Exception {
        return ResponseEntity.ok().body(shiftService.create(shiftDto));
    }

    @PutMapping("/shift/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ShiftDto shiftDto) throws Exception {
        return ResponseEntity.ok().body(shiftService.update(id, shiftDto));
    }

    @DeleteMapping("/shift/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        shiftService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully!");
    }
}