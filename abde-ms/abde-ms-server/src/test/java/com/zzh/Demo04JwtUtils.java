 package com.zzh;

import com.zzh.constants.JwtTokenConstant;
import com.zzh.utils.JwtTokeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class Demo04JwtUtils {
    @Test
    public void generate() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", 10000);
        claims.put("username", "zz");
        claims.put("age", "123456");
        String token = JwtTokeyUtils.generateJwtToken(claims);

        System.out.println("token = " + token);
    }

    @Test
    public void parseJwt() {
        String key = JwtTokenConstant.SECRET_KEY;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAwMDAsImFnZSI6IjEyMzQ1NiIsInVzZXJuYW1lIjoienoiLCJleHAiOjE3Nzg3NjI1NDl9.ZNkrkQvth7U4gyfsuhme7MPjdKA0LoF5trgJe0M2Sl0";

        Map<String, Object> claims = JwtTokeyUtils.parseJwtToken(token);
        System.out.println("claims = " + claims);
    }
}
