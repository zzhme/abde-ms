package com.zzh.constants;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Base64;

// Jwt令牌常量
public class JwtTokenConstant {
    // 密钥（至少32字节 = 256 bits）
    private static final String SECRET_KEY_STRING = "abde-ms-jwt-secret-key-2024-secure-enough-for-hs256";
    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    // 过期时间
    public static final Long EXPIRED_TIME = 1000*60*1000L;
}
