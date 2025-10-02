package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${jwt.secret-key:myDefaultSecretKeyThatIsAtLeast256BitsLongForHS256Algorithm}")
  private String secretKey;

  @Value("${jwt.expiration:86400000}")
  private Long jwtExpiration;

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKeyParam) {
    secretKey = secretKeyParam;
  }

  public Long getJwtExpiration() {
    return jwtExpiration;
  }

  public void setJwtExpiration(Long jwtExpirationParam) {
    jwtExpiration = jwtExpirationParam;
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(String username, String role, Integer employeeId) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);
    if (employeeId != null) {
      claims.put("employeeId", employeeId);
    }
    return createToken(claims, username);
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(getSigningKey())
        .compact();
  }

  public Boolean isTokenValid(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return (extractedUsername.equals(username)) && !isTokenExpired(token);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

  public Integer extractEmployeeId(String token) {
    return extractClaim(token, claims -> claims.get("employeeId", Integer.class));
  }
}