package com.example.yangiliklarwebsite.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsersDto {
    @NotNull(message = "ism maydonchasini to'ldiring")
    private String ism;
    @NotNull(message = "familiya maydonchasini to'ldiring")
    private String fam;
    @NotNull(message = "email(username) maydonchasini to'ldiring")
    private String username;
    @NotNull(message = "parol(password) maydonchasini to'ldiring")
    private String password;
    @NotNull(message = "repassword(takroriy kod) maydonchasini to'ldiring")
    private String repassword;
}
