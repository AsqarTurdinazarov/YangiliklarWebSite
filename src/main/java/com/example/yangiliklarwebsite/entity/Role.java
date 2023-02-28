package com.example.yangiliklarwebsite.entity;

import com.example.yangiliklarwebsite.entity.enums.Huquqlar;
import com.example.yangiliklarwebsite.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Lavozim")
public class Role extends AbstractEntity {
    @Column(nullable = false)
    private String nomi;
    @Column(nullable = false)
    private String izoh;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Huquqlar> huquqlarList;
}
