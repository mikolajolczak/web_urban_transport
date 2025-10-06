package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for user authentication, registration, and
 * session management.
 *
 * <p>Provides endpoints for login, logout, token validation, fetching the
 * current user, and user registration.
 * Uses JWT tokens stored in HTTP-only cookies to manage sessions.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

  /**
   * Name of the HTTP cookie used to store JWT tokens.
   */
  private static final String JWT_COOKIE_NAME = "jwt-token";
  /**
   * Maximum age of the JWT cookie in seconds (24 hours).
   */
  private static final int COOKIE_MAX_AGE = 24 * 60 * 60;
  /**
   * Indicates if the JWT cookie should only be sent over HTTPS.
   */
  private static final boolean COOKIE_SECURE = false;
  /**
   * Indicates if the JWT cookie is HTTP-only (not accessible via JavaScript).
   */
  private static final boolean COOKIE_HTTP_ONLY = true;
  /**
   * Service handling user-related operations, including authentication and
   * user creation.
   */
  private final UserService userService;
  /**
   * Service responsible for generating and validating JWT tokens.
   */
  private final JwtService jwtService;

  /**
   * Constructs a new {@code UserController} with the given services.
   *
   * @param userServiceParam the service for user-related operations
   * @param jwtServiceParam  the service for JWT token management
   */
  public UserController(final UserService userServiceParam,
                        final JwtService jwtServiceParam) {
    this.userService = userServiceParam;
    this.jwtService = jwtServiceParam;
  }

  /**
   * Authenticates a user with the provided credentials.
   *
   * <p>On successful authentication, generates a JWT token and sets it in an
   * HTTP-only cookie.
   * Returns the user's role, employee ID (if any), and username in the
   * response body.
   * </p>
   *
   * @param loginRequest the login request containing username and password
   * @param response     the HTTP response where the JWT cookie is set
   * @return {@link ResponseEntity} containing {@link LoginResponse} on
   *     success, or 401 Unauthorized on failure
   */
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody final LoginRequest loginRequest,
      final HttpServletResponse response) {

    try {
      User user = userService.login(loginRequest.getUsername(),
          loginRequest.getPassword());
      Integer employeeId =
          (user.getEmployee() != null) ? user.getEmployee().getId() : null;

      String token =
          jwtService.generateToken(user.getUsername(), user.getRole(),
              employeeId);

      Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, token);
      jwtCookie.setHttpOnly(COOKIE_HTTP_ONLY);
      jwtCookie.setSecure(COOKIE_SECURE);
      jwtCookie.setPath("/");
      jwtCookie.setMaxAge(COOKIE_MAX_AGE);
      response.addCookie(jwtCookie);

      LoginResponse loginResponse =
          new LoginResponse(user.getRole(), employeeId, user.getUsername());
      return ResponseEntity.ok(loginResponse);

    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  /**
   * Logs out the current user by invalidating the JWT cookie.
   *
   * @param response the HTTP response where the cookie will be cleared
   * @return {@link ResponseEntity} with HTTP status 200 OK
   */
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(final HttpServletResponse response) {
    Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, null);
    jwtCookie.setHttpOnly(COOKIE_HTTP_ONLY);
    jwtCookie.setSecure(COOKIE_SECURE);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(0);
    response.addCookie(jwtCookie);

    return ResponseEntity.ok().build();
  }

  /**
   * Validates the JWT token from the request cookie.
   *
   * @param request the HTTP request containing the JWT cookie
   * @return {@link ResponseEntity} containing {@code true} if the token is
   *     valid, {@code false} otherwise
   */
  @GetMapping("/validate")
  public ResponseEntity<Boolean> validateToken(
      final HttpServletRequest request) {
    try {
      Optional<String> token = extractTokenFromCookie(request);
      if (token.isEmpty()) {
        return ResponseEntity.ok(false);
      }

      String username = jwtService.extractUsername(token.get());
      return ResponseEntity.ok(jwtService.isTokenValid(token.get(), username));

    } catch (Exception e) {
      return ResponseEntity.ok(false);
    }
  }

  /**
   * Retrieves information about the currently authenticated user.
   *
   * @param request the HTTP request containing the JWT cookie
   * @return {@link ResponseEntity} containing {@link LoginResponse} if the
   *     user is authenticated, or 401 Unauthorized if not
   */
  @GetMapping("/me")
  public ResponseEntity<LoginResponse> getCurrentUser(
      final HttpServletRequest request) {
    try {
      Optional<String> token = extractTokenFromCookie(request);
      if (token.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String username = jwtService.extractUsername(token.get());
      if (!jwtService.isTokenValid(token.get(), username)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      User user = userService.findByUsername(username).orElse(null);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      LoginResponse loginResponse = new LoginResponse(
          user.getRole(),
          (user.getEmployee() != null) ? user.getEmployee().getId() : -1,
          username
      );

      return ResponseEntity.ok(loginResponse);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  /**
   * Registers a new user with the provided username, password, and role.
   *
   * @param registerRequest the registration request containing username,
   *                        password, and role
   * @return {@link ResponseEntity} with HTTP status 201 Created on success,
   *     or 400 Bad Request if registration fails
   */
  @PostMapping("/register")
  public ResponseEntity<String> register(
      @RequestBody final RegisterRequest registerRequest) {
    try {
      userService.createUser(
          registerRequest.getUsername(),
          registerRequest.getPassword(),
          registerRequest.getRole()
      );
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  /**
   * Extracts the JWT token from the request cookies.
   *
   * @param request the HTTP request containing cookies
   * @return an {@link Optional} containing the JWT token if present,
   *     otherwise {@link Optional#empty()}
   */
  private Optional<String> extractTokenFromCookie(
      final HttpServletRequest request) {
    if (request.getCookies() == null) {
      return Optional.empty();
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst();
  }
}
