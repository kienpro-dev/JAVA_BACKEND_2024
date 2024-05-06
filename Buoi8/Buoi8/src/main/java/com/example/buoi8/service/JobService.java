package com.example.buoi8.service;

import com.example.buoi8.dto.JobDto;
import com.example.buoi8.entity.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {
    Job create(JobDto jobDto) throws Exception;
    List<Job> reads();
    Job read(Long id) throws Exception;
    Job update(Long id, JobDto jobDto) throws Exception;
    void delete(Long id) throws  Exception;
}
