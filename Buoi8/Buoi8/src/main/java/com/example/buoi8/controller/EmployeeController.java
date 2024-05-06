package com.example.buoi8.controller;

import com.example.buoi8.dto.EmployeeDto;
import com.example.buoi8.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(employeeService.reads());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(employeeService.read(id));
    }

    @PostMapping("/employee")
    public ResponseEntity<?> create(@RequestBody EmployeeDto employeeDto) throws Exception {
        return ResponseEntity.ok().body(employeeService.create(employeeDto));
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) throws Exception {
        return ResponseEntity.ok().body(employeeService.update(id, employeeDto));
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        employeeService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully!");
    }
}
