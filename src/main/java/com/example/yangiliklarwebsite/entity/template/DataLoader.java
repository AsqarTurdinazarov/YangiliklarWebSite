package com.example.yangiliklarwebsite.entity.template;

import com.example.yangiliklarwebsite.entity.Role;
import com.example.yangiliklarwebsite.entity.Users;
import com.example.yangiliklarwebsite.entity.enums.Huquqlar;
import com.example.yangiliklarwebsite.repository.RoleRepository;
import com.example.yangiliklarwebsite.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.yangiliklarwebsite.entity.enums.Huquqlar.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value(value = "${spring.sql.init.mode}")
    private String boshqaruv;

    @Override
    public void run(String... args) throws Exception {
        if (boshqaruv.equals("always")){
            Huquqlar[] huquqlars = Huquqlar.values();
            Role admin = roleRepository.save(new Role("admin","Platforma egasi", Arrays.asList(huquqlars)));
            Role user = roleRepository.save(new Role("user","Oddiy foydalanuvchi", Arrays.asList(ADDCOMMENT,EDITMYCOMMENT,DELETEMYCOMMENT,READPOST,READCOMMENT,EDITMYINFO)));
            usersRepository.save(new Users("Asqar","Turdinazarov","turdinazarovasqar355@gmail.com",passwordEncoder.encode("1234"),true,admin));
            usersRepository.save(new Users("Ali","Valiyev","turdinazarovasqar@gmail.com",passwordEncoder.encode("1111"),true,user));
        }
    }
}
