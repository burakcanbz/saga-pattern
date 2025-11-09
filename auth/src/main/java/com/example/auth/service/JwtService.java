package com.example.auth.service;

import com.example.auth.entity.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration.time.ms}")
    private long EXPIRATION_TIME_MS;

    public String generateToken(Auth authUser) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", authUser.getEmail());
        extraClaims.put("username", authUser.getUsername());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(authUser.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}