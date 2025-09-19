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

@RestController
@RequestMapping("/api/auth")
public class UserController {
  private final UserService userService;
  private final JwtService jwtService;

  private static final String JWT_COOKIE_NAME = "jwt-token";
  private static final int COOKIE_MAX_AGE = 24 * 60 * 60;
  private static final boolean COOKIE_SECURE = false;
  private static final boolean COOKIE_HTTP_ONLY = true;

  public UserController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody LoginRequest loginRequest,
      HttpServletResponse response) {
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

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response) {
    Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, null);
    jwtCookie.setHttpOnly(COOKIE_HTTP_ONLY);
    jwtCookie.setSecure(COOKIE_SECURE);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(0);

    response.addCookie(jwtCookie);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/validate")
  public ResponseEntity<Boolean> validateToken(HttpServletRequest request) {
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

  @GetMapping("/me")
  public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
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
       return ResponseEntity.ok(user);


    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(
      @RequestBody RegisterRequest registerRequest) {
    try {
      User newUser = userService.createUser(
          registerRequest.getUsername(),
          registerRequest.getPassword(),
          registerRequest.getRole()
      );

      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  private Optional<String> extractTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null) {
      return Optional.empty();
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst();
  }

}