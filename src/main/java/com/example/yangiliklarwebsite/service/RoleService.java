package com.example.yangiliklarwebsite.service;

import com.example.yangiliklarwebsite.entity.Role;
import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.payload.RoleDto;
import com.example.yangiliklarwebsite.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public ApiRespons postRole(RoleDto roleDto) {
        boolean b = roleRepository.existsByNomi(roleDto.getNomi());
        if (b){ return new ApiRespons("Bunday lavozim allaqachon mavjud",false);}
        Role role = new Role(
                roleDto.getNomi(),
                roleDto.getIzoh(),
                roleDto.getHuquqlarList()
        );
        roleRepository.save(role);
        return new ApiRespons("Lavozim ma'lumotlari muvaffaqiyatli saqlandi",true);
    }

    public ApiRespons edit(Integer id, RoleDto roleDto) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()){
            Role role = byId.get();
            role.setNomi(roleDto.getNomi());
            role.setIzoh(roleDto.getIzoh());
            role.setHuquqlarList(roleDto.getHuquqlarList());
            roleRepository.save(role);
            return new ApiRespons(id + " - Idli lavozim tahrirlandi",true);
        }
        return new ApiRespons("Bunday Idli lavozim mavjud emas",false);
    }

    public ApiRespons delete(Integer id) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()){
            roleRepository.deleteById(id);
            return new ApiRespons(id+"-Idli lavozim muvaffaqiyatli o'chirildi",true);
        }
        return new ApiRespons("Bunday Idli lavozim mavjud emas",false);
    }

    public ApiRespons readid(Integer id) {
        Optional<Role> byId = roleRepository.findById(id);
        if (byId.isPresent()){
            String S = "";
            S = byId.get().toString();
            return new ApiRespons(S,true);
        }
        return new ApiRespons("Bunday Idli lavozim mavjud emas",false);
    }

    public ApiRespons getall() {
        List<Role> all = roleRepository.findAll();
        if (!all.isEmpty()){
            String S = "";
            for (Role role : all) {
                S += role + "\n";
            }
            return new ApiRespons(S,true);
        }
        return new ApiRespons("Ushbu bazada ma'lumotlar mavjud emas",false);
    }
}
