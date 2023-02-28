package com.example.yangiliklarwebsite.repository;

import com.example.yangiliklarwebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByNomi(String nomi);
    boolean existsByNomi(String nomi);
}
