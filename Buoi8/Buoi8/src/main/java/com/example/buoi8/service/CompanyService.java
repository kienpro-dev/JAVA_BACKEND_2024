package com.example.buoi8.service;

import com.example.buoi8.dto.CompanyDto;
import com.example.buoi8.entity.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    Company create(CompanyDto companyDto);
    List<Company> reads();
    Company read(Long id) throws Exception;
    Company update(Long id, CompanyDto companyDto) throws Exception;
    void delete(Long id) throws Exception;
}
