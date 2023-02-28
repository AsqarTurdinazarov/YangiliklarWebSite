package com.example.yangiliklarwebsite.controller;

import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.payload.RoleDto;
import com.example.yangiliklarwebsite.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PreAuthorize(value = "hasAnyAuthority('ADDROLE')")
    @PostMapping("/add")
    public HttpEntity<?> Joylash(@RequestBody RoleDto roleDto){
        ApiRespons apiRespons = roleService.postRole(roleDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('EDITROLE')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> Tahrir(@RequestBody RoleDto roleDto,@PathVariable Integer id){
        ApiRespons apiRespons = roleService.edit(id,roleDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('DELETEROLE')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> Delete(@PathVariable Integer id){
        ApiRespons apiRespons = roleService.delete(id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('READROLE')")
    @GetMapping("/readid/{id}")
    public HttpEntity<?> ReadId(@PathVariable Integer id){
        ApiRespons apiRespons = roleService.readid(id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('READROLE')")
    @GetMapping("/readall")
    public HttpEntity<?> ReadAll(){
        ApiRespons apiRespons = roleService.getall();
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
