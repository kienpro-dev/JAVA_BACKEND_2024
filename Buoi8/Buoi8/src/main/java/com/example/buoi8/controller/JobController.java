package com.example.buoi8.controller;

import com.example.buoi8.dto.JobDto;
import com.example.buoi8.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping("/job")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(jobService.reads());
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(jobService.read(id));
    }

    @PostMapping("/job")
    public ResponseEntity<?> create(@RequestBody JobDto jobDto) throws Exception {
        return ResponseEntity.ok().body(jobService.create(jobDto));
    }

    @PutMapping("/job/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody JobDto jobDto) throws Exception {
        return ResponseEntity.ok().body(jobService.update(id, jobDto));
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        jobService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully!");
    }
}
