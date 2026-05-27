package com.zzh.utils;

import com.zzh.constants.JwtTokenConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;


public class JwtTokeyUtils {
    private JwtTokeyUtils() {
    }

    //生成token
    public static String generateJwtToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JwtTokenConstant.SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + JwtTokenConstant.EXPIRED_TIME))
                .compact();

        return token;
    }


    // 解析token
    public static Map<String,Object> parseJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtTokenConstant.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
