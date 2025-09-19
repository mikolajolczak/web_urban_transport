package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User login(String username, String rawPassword) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Username not found"));

    if (!user.isActive()) {
      throw new RuntimeException("User account is inactive");
    }

    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    return user;
  }

  public User createUser(String username, String rawPassword, String role) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new RuntimeException("Username already exists");
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(rawPassword));
    user.setRole(role);
    user.setActive(true);

    return userRepository.save(user);
  }
}