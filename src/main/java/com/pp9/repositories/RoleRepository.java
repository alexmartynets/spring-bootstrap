package com.pp9.repositories;

import com.pp9.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long id);
    Role findByRole(String role);
    List<Role> findAll();
}

