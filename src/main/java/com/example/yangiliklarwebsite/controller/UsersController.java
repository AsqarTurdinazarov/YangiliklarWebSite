package com.example.yangiliklarwebsite.controller;

import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.payload.LoginDto;
import com.example.yangiliklarwebsite.payload.UserRoleEditorDto;
import com.example.yangiliklarwebsite.payload.UsersDto;
import com.example.yangiliklarwebsite.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    public HttpEntity<?> Registration(@Valid @RequestBody UsersDto usersDto){
        ApiRespons apiRespons = usersService.register(usersDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @GetMapping("/tasdiqlash")
    public HttpEntity<?> Tastiqlash(@RequestParam String email,@RequestParam String emailCode){
        ApiRespons apiRespons = usersService.faollashtirish(email,emailCode);
        return ResponseEntity.status(apiRespons.isHolat()?200:409).body(apiRespons.getXabar());
    }

    @PostMapping("/logIn")
    public HttpEntity<?> LogIn(@RequestBody LoginDto loginDto){
        ApiRespons apiRespons = usersService.userLogin(loginDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:409).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLETAYINLAMOQ')")
    @PutMapping("/editRole/{username}")
    public HttpEntity<?> UserRoleEditor(@PathVariable String username, @RequestBody UserRoleEditorDto userRoleEditorDto){
        ApiRespons apiRespons = usersService.editRole(username,userRoleEditorDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:409).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('EDITMYINFO')")
    @PutMapping("/editmyinfo/{id}")
    public HttpEntity<?> EditMyInfo(@PathVariable Integer id,@RequestBody UsersDto usersDto){
        ApiRespons apiRespons = usersService.editmyinfo(id,usersDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
