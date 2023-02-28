package com.example.yangiliklarwebsite.entity;

import com.example.yangiliklarwebsite.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post extends AbstractEntity {

    @Column(nullable = false,columnDefinition = "text")
    private String sarlavha;
    @Column(nullable = false,columnDefinition = "text")
    private String matni;
    @Column(nullable = false)
    private String url;
}
