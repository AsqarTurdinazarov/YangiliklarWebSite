package com.example.yangiliklarwebsite.token;

import com.example.yangiliklarwebsite.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenGenerator {
        String kalit = "kalit1";
        public String TokenOlish(String UserName, Role role){

            long vaqt = 60*60*1000*24;
            Date yashashVaqti = new Date(System.currentTimeMillis()+vaqt);

            String token = Jwts
                    .builder()
                    .setSubject(UserName)
                    .setIssuedAt(new Date())
                    .setExpiration(yashashVaqti)
                    .claim("Lavozim",role.getNomi())
                    .signWith(SignatureAlgorithm.HS512, kalit)
                    .compact();
            return token;
        }

        public String OpenFilter(String token){
            String userName = Jwts
                    .parser()
                    .setSigningKey(kalit)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return userName;
        }

        public boolean TokenTekshirish(String token){
            try {
                Jwts
                        .parser()
                        .setSigningKey(kalit)
                        .parseClaimsJws(token);
                return true;
            }catch (Exception e){
                return false;
            }
        }

    }
