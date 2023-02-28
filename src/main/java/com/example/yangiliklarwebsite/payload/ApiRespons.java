package com.example.yangiliklarwebsite.payload;

import com.example.yangiliklarwebsite.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiRespons {
    private String xabar;
    private boolean holat;
    private Role role;

    public ApiRespons(String xabar, boolean holat) {
        this.xabar = xabar;
        this.holat = holat;
    }
}
