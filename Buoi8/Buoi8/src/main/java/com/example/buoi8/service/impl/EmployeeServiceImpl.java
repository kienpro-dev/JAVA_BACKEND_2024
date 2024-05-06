package com.example.buoi8.service.impl;

import com.example.buoi8.dto.EmployeeDto;
import com.example.buoi8.entity.Company;
import com.example.buoi8.entity.Employee;
import com.example.buoi8.repository.CompanyRepository;
import com.example.buoi8.repository.EmployeeRepository;
import com.example.buoi8.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Employee create(EmployeeDto employeeDto) throws Exception {
        Optional<Company> company = Optional.ofNullable(companyRepository.findById(employeeDto.getCompanyId()).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setCompany(company.get());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> reads() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee read(Long id) throws Exception {
        return employeeRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        });
    }

    @Override
    public Employee update(Long id, EmployeeDto employeeDto) throws Exception {
        Optional<Company> company = Optional.ofNullable(companyRepository.findById(employeeDto.getCompanyId()).orElseThrow(() -> {
            return new Exception("Not found");
        }));

        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findById(employeeDto.getCompanyId()).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        employee.get().setName(employeeDto.getName());
        employee.get().setCompany(company.get());
        return employee.get();
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        employeeRepository.deleteById(id);
    }
}
