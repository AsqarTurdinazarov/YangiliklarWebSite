package com.example.yangiliklarwebsite.repository;

import com.example.yangiliklarwebsite.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Optional<Comment> findByIdAndCreateBy(Integer id, Integer createBy);
}
