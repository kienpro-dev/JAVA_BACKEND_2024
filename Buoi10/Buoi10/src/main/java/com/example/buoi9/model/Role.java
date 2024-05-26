package com.example.buoi9.model;

import com.example.buoi9.enums.EnumRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private EnumRole roleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<User> users;
}
