package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for creating, parsing, and validating JWT (JSON Web Tokens).
 */
@Service
public class JwtService {

  /**
   * Secret key used to sign JWTs.
   *
   * <p>Loaded from application properties with a default fallback.
   * </p>
   */
  @Value(
      "${jwt.secret-key"
          + ":myDefaultSecretKeyThatIsAtLeast256BitsLongForHS256Algorithm}")
  private String secretKey;

  /**
   * JWT expiration time in milliseconds.
   *
   * <p>Default is 24 hours (86400000 ms).
   * </p>
   */
  @Value("${jwt.expiration:86400000}")
  private Long jwtExpiration;

  /**
   * Sets the secret key used for signing JWTs.
   *
   * @param secretKeyParam the new secret key
   */
  public void setSecretKey(final String secretKeyParam) {
    secretKey = secretKeyParam;
  }

  /**
   * Sets the JWT expiration time in milliseconds.
   *
   * @param jwtExpirationParam the new expiration time
   */
  public void setJwtExpiration(final Long jwtExpirationParam) {
    jwtExpiration = jwtExpirationParam;
  }

  /**
   * Returns the {@link SecretKey} object used for signing JWTs.
   *
   * @return the signing key
   */
  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  /**
   * Extracts the username (subject) from a given JWT token.
   *
   * @param token the JWT token
   * @return the username embedded in the token
   */
  public String extractUsername(final String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a specific claim from a JWT token using a claims resolver
   * function.
   *
   * @param <T>            the type of the claim
   * @param token          the JWT token
   * @param claimsResolver a function to extract a claim from {@link Claims}
   * @return the extracted claim
   */
  public <T> T extractClaim(final String token,
                            final Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates a JWT token for a given username, role, and optionally an
   * employee ID.
   *
   * @param username   the username (subject) for the token
   * @param role       the user's role
   * @param employeeId the employee ID (nullable)
   * @return a signed JWT token
   */
  public String generateToken(final String username, final String role,
                              final Integer employeeId) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);
    if (employeeId != null) {
      claims.put("employeeId", employeeId);
    }
    return createToken(claims, username);
  }

  /**
   * Creates a JWT token with the specified claims and subject.
   *
   * @param claims  additional claims to include in the token
   * @param subject the subject (username) of the token
   * @return a signed JWT token
   */
  private String createToken(final Map<String, Object> claims,
                             final String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(getSigningKey())
        .compact();
  }

  /**
   * Validates if a JWT token is valid for a given username.
   *
   * @param token    the JWT token
   * @param username the username to validate against
   * @return true if token is valid and not expired, false otherwise
   */
  public Boolean isTokenValid(final String token, final String username) {
    final String extractedUsername = extractUsername(token);
    return (extractedUsername.equals(username)) && !isTokenExpired(token);
  }

  /**
   * Checks if the JWT token is expired.
   *
   * @param token the JWT token
   * @return true if the token is expired, false otherwise
   */
  private Boolean isTokenExpired(final String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from a JWT token.
   *
   * @param token the JWT token
   * @return the expiration {@link Date} of the token
   */
  public Date extractExpiration(final String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts all claims from a JWT token.
   *
   * @param token the JWT token
   * @return all claims in the token
   */
  private Claims extractAllClaims(final String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build()
        .parseSignedClaims(token).getPayload();
  }

  /**
   * Extracts the user's role from a JWT token.
   *
   * @param token the JWT token
   * @return the role embedded in the token
   */
  public String extractRole(final String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

  /**
   * Extracts the employee ID from a JWT token.
   *
   * @param token the JWT token
   * @return the employee ID embedded in the token, or null if not present
   */
  public Integer extractEmployeeId(final String token) {
    return extractClaim(token,
        claims -> claims.get("employeeId", Integer.class));
  }
}
