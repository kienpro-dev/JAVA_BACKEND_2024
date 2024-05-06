package com.example.buoi8.service.impl;

import com.example.buoi8.dto.CompanyDto;
import com.example.buoi8.entity.Company;
import com.example.buoi8.repository.CompanyRepository;
import com.example.buoi8.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company create(CompanyDto companyDto) {
        return companyRepository.save(new Company(companyDto.getName()));
    }

    @Override
    public List<Company> reads() {
        return companyRepository.findAll();
    }

    @Override
    public Company read(Long id) throws Exception {
        return companyRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        });
    }

    @Override
    public Company update(Long id, CompanyDto companyDto) throws Exception {
        Optional<Company> company = Optional.ofNullable(companyRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        company.get().setName(companyDto.getName());
        return company.get();
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<Company> company = Optional.ofNullable(companyRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        companyRepository.deleteById(id);
    }
}
