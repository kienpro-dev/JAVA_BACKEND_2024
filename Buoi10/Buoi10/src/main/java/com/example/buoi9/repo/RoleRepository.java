package com.example.buoi9.repo;

import com.example.buoi9.enums.EnumRole;
import com.example.buoi9.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleName(EnumRole enumRole);
}
