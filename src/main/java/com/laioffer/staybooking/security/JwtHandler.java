package com.laioffer.staybooking.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtHandler {

  final Key signingKey;

  public JwtHandler(@Value("${staybooking.jwt.secret-key}") String secretKey) {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    signingKey = Keys.hmacShaKeyFor(bytes);  // hmac: hash-based message authentication code
  }

  public String parsedUsername(String token) {
    return Jwts.parser()
        .setSigningKey(signingKey)
        .build()
        .parseClaimsJws(token) // will throw an exception if the token is invalid
        .getBody()
        .getSubject();
  }

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(signingKey, SignatureAlgorithm.HS256)
        .compact();
  }
}
