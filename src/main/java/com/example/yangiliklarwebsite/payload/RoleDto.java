package com.example.yangiliklarwebsite.payload;

import com.example.yangiliklarwebsite.entity.enums.Huquqlar;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleDto {
    @NotNull(message = "Lavozim nomi berilmadi")
    private String nomi;
    @NotNull(message = "Lavozimga izoh berilmadi")
    private String izoh;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Huquqlar> huquqlarList;

}
