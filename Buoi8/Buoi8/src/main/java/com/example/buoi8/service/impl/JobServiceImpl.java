package com.example.buoi8.service.impl;

import com.example.buoi8.dto.JobDto;
import com.example.buoi8.entity.Employee;
import com.example.buoi8.entity.Job;
import com.example.buoi8.repository.EmployeeRepository;
import com.example.buoi8.repository.JobRepository;
import com.example.buoi8.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Job create(JobDto jobDto) throws Exception {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findById(jobDto.getEmployeeId()).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        Job job = new Job();
        job.setName(jobDto.getName());
        job.setEmployee(employee.get());
        return jobRepository.save(job);
    }

    @Override
    public List<Job> reads() {
        return jobRepository.findAll();
    }

    @Override
    public Job read(Long id) throws Exception {
        return jobRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        });
    }

    @Override
    public Job update(Long id, JobDto jobDto) throws Exception {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findById(jobDto.getEmployeeId()).orElseThrow(() -> {
            return new Exception("Not found");
        }));

        Optional<Job> job = Optional.ofNullable(jobRepository.findById(jobDto.getEmployeeId()).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        job.get().setName(jobDto.getName());
        job.get().setEmployee(employee.get());
        return job.get();
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<Job> job = Optional.ofNullable(jobRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        jobRepository.deleteById(id);
    }
}
