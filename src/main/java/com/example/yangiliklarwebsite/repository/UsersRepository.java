package com.example.yangiliklarwebsite.repository;

import com.example.yangiliklarwebsite.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    boolean existsByUsername(String username);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByUsernameAndEmailCode(String username,String code);
}
