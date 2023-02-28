package com.example.yangiliklarwebsite.repository;

import com.example.yangiliklarwebsite.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> {
    boolean existsByMatni(String matni);
    Optional<Post> findByIdAndCreateBy(Integer id, Integer createBy);
}
