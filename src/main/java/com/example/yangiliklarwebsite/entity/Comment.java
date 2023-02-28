package com.example.yangiliklarwebsite.entity;

import com.example.yangiliklarwebsite.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Comment extends AbstractEntity {
    @Column(nullable = false,columnDefinition = "text")
    private String matni;

    @ManyToOne
    private Post post;
}
