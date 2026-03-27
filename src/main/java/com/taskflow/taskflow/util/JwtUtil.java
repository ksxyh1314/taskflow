package com.taskflow.taskflow.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // 生成 Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 从 Token 解析用户名
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // 验证 Token 是否过期
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 验证 Token 是否有效
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // 解析 Claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 生成签名 Key
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
