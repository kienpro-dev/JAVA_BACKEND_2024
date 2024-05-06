package com.example.buoi8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ca làm việc
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String start;

    public Shift(String start, String end) {
        this.start = start;
        this.end = end;
    }

    private String end;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "employee_shift",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "shift_id")
    )
    private List<Employee> employees = new ArrayList<>();
}
