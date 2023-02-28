package com.example.yangiliklarwebsite.entity;

import com.example.yangiliklarwebsite.entity.enums.Huquqlar;
import com.example.yangiliklarwebsite.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Foydalanuvchilar")
public class Users extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String ism;
    @Column(nullable = false)
    private String fam;
    @Email
    @Column(nullable = false,unique = true)
    private String username;        //  email username sifatida
    @Column(nullable = false)
    private String password;

    private String emailCode;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = false;
    @ManyToOne
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Huquqlar> huquqlarList = this.role.getHuquqlarList();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Huquqlar huquqlar : huquqlarList) {
            grantedAuthorityList.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return huquqlar.name();
                }
            });
        }
        return grantedAuthorityList;
    }


    public Users(String ism, String fam, String username, String password, boolean enabled,Role role) {
        this.ism = ism;
        this.fam = fam;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
}
