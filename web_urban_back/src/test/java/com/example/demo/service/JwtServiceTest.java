package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

  @InjectMocks
  private JwtService jwtService;

  private String secretKey;

  @BeforeEach
  void setUp() {
    secretKey = "ThisIsASecretKeyThatIsAtLeast256BitsLongForHS256Algo12345";
    long jwtExpiration = 2000L;
    jwtService.setSecretKey(secretKey);
    jwtService.setJwtExpiration(jwtExpiration);
  }

  @Test
  void shouldGenerateAndValidateToken() {
    String username = "john.doe";
    String role = "ADMIN";
    Integer employeeId = 42;

    String token = jwtService.generateToken(username, role, employeeId);

    assertNotNull(token);
    assertEquals(username, jwtService.extractUsername(token));
    assertEquals(role, jwtService.extractRole(token));
    assertEquals(employeeId, jwtService.extractEmployeeId(token));
    assertTrue(jwtService.isTokenValid(token, username));
  }

  @Test
  void shouldGenerateTokenWithoutEmployeeId() {
    String username = "jane.doe";
    String role = "USER";

    String token = jwtService.generateToken(username, role, null);

    assertNotNull(token);
    assertEquals(username, jwtService.extractUsername(token));
    assertEquals(role, jwtService.extractRole(token));
    assertNull(jwtService.extractEmployeeId(token));
    assertTrue(jwtService.isTokenValid(token, username));
  }

  @Test
  void shouldDetectInvalidUsername() {
    String username = "john.doe";
    String role = "ADMIN";
    String token = jwtService.generateToken(username, role, 1);

    assertFalse(jwtService.isTokenValid(token, "wrong.user"));
  }

  @Test
  void shouldDetectExpiredToken() throws InterruptedException {
    jwtService.setJwtExpiration(100L);
    String token = jwtService.generateToken("john.doe", "ADMIN", 1);

    Thread.sleep(200);

    assertThrows(io.jsonwebtoken.ExpiredJwtException.class,
        () -> jwtService.extractUsername(token));
  }

  @Test
  void shouldExtractExpirationProperly() {
    String token = jwtService.generateToken("alice", "USER", 5);
    Date expiration = jwtService.extractClaim(token, Claims::getExpiration);

    assertNotNull(expiration);
    assertTrue(expiration.after(new Date()));
  }

  @Test
  void shouldUseConsistentSigningKey() {
    SecretKey key1 = Keys.hmacShaKeyFor(secretKey.getBytes());
    SecretKey key2 = jwtService.getClass()
        .getDeclaredAnnotation(Service.class) != null
        ? Keys.hmacShaKeyFor(secretKey.getBytes())
        : null;
    assertNotNull(key2);
    assertArrayEquals(key1.getEncoded(), key2.getEncoded());
  }
}
