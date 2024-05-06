package com.example.buoi8.service.impl;

import com.example.buoi8.dto.ShiftDto;
import com.example.buoi8.entity.Employee;
import com.example.buoi8.entity.Shift;
import com.example.buoi8.repository.EmployeeRepository;
import com.example.buoi8.repository.ShiftRepository;
import com.example.buoi8.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShiftServiceImpl implements ShiftService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public Shift create(ShiftDto shiftDto) throws Exception {
        return shiftRepository.save(new Shift(shiftDto.getStart(), shiftDto.getEnd()));
    }

    @Override
    public List<Shift> reads() {
        return shiftRepository.findAll();
    }

    @Override
    public Shift read(Long id) throws Exception {
        return shiftRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        });
    }

    @Override
    public Shift update(Long id, ShiftDto shiftDto) throws Exception {
        Optional<Shift> shift = Optional.ofNullable(shiftRepository.findById(id).orElseThrow(() -> {
            return new Exception("Not found");
        }));
        shift.get().setStart(shiftDto.getStart());
        shift.get().setEnd(shiftDto.getEnd());
        return shift.get();
    }

    @Override
    public void delete(Long id) throws Exception {
        shiftRepository.deleteById(id);
    }
}
