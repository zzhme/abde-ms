package com.zzh;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@SpringBootTest
public class Demo03Jwt {
    @Test
    public void generate() {
        String key = UUID.randomUUID().toString().replace("-", "");
        System.out.println("key = " + key);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", 10000);
        claims.put("username", "zz");
        claims.put("age", "123456");
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000L))
                .compact();

        System.out.println("token = " + token);
    }

    @Test
    public void parseJwt() {
        String key = "6254fbf28ae54679889d933c67d1caf3";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAwMDAsImFnZSI6IjEyMzQ1NiIsInVzZXJuYW1lIjoienoiLCJleHAiOjE3Nzg3NjEyMDV9.4VZr7DYZ1RO2T_hjiqt-HvlvKV-UbyyrzxzmASkOyFU";

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        System.out.println("claims = " + claims);
    }
}
