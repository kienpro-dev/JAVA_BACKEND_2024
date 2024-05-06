package com.example.buoi8.service;

import com.example.buoi8.dto.CompanyDto;
import com.example.buoi8.dto.EmployeeDto;
import com.example.buoi8.entity.Company;
import com.example.buoi8.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    Employee create(EmployeeDto employeeDto) throws Exception;
    List<Employee> reads();
    Employee read(Long id) throws Exception;
    Employee update(Long id, EmployeeDto employeeDto) throws Exception;
    void delete(Long id) throws Exception;
}
