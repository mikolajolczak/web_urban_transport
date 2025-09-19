package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
  private final UserService userService;
  private final JwtService jwtService;

  public UserController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    try {
      User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
      Integer employeeId = (user.getEmployee() != null) ? user.getEmployee().getId() : null;

      String token = jwtService.generateToken(user.getUsername(), user.getRole(), employeeId);

      LoginResponse response = new LoginResponse(token, user.getRole(), employeeId, user.getUsername());

      return ResponseEntity.ok(response);

    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @GetMapping("/validate")
  public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.ok(false);
      }

      String token = authHeader.substring(7);
      String username = jwtService.extractUsername(token);

      return ResponseEntity.ok(jwtService.isTokenValid(token, username));
    } catch (Exception e) {
      return ResponseEntity.ok(false);
    }
  }

  @GetMapping("/me")
  public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String token = authHeader.substring(7);
      String username = jwtService.extractUsername(token);

      if (!jwtService.isTokenValid(token, username)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      // User user = userRepository.findByUsername(username).orElse(null);

      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}