package com.example.buoi8.service;

import com.example.buoi8.dto.ShiftDto;
import com.example.buoi8.entity.Shift;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShiftService {
    Shift create(ShiftDto shiftDto) throws Exception;
    List<Shift> reads();
    Shift read(Long id) throws Exception;
    Shift update(Long id, ShiftDto shiftDto) throws Exception;
    void delete(Long id) throws  Exception;
}
