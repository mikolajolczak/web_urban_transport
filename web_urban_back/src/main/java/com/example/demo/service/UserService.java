package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing {@link User} entities.
 *
 * <p>Provides operations for user authentication, creation, and retrieval by
 * username.
 * </p>
 */
@Service
public class UserService {

  /**
   * Repository for accessing user data.
   */
  private final UserRepository userRepository;

  /**
   * Password encoder for hashing and verifying passwords.
   */
  private final PasswordEncoder passwordEncoder;

  /**
   * Constructs a new {@code UserService} with the specified dependencies.
   *
   * @param userRepositoryParam  the repository used to access user data
   * @param passwordEncoderParam the password encoder used for password
   *                             operations
   */
  public UserService(final UserRepository userRepositoryParam,
                     final PasswordEncoder passwordEncoderParam) {
    this.userRepository = userRepositoryParam;
    this.passwordEncoder = passwordEncoderParam;
  }

  /**
   * Authenticates a user with the provided username and raw password.
   *
   * @param username    the username of the user attempting to log in
   * @param rawPassword the raw password to verify against the stored hash
   * @return the authenticated {@link User} entity
   * @throws RuntimeException if the username does not exist, the account is
   *                          inactive,
   *                          or the password is invalid
   */
  public User login(final String username, final String rawPassword) {
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

  /**
   * Creates a new user with the specified username, password, and role.
   *
   * @param username    the username for the new user
   * @param rawPassword the raw password to be hashed and stored
   * @param role        the role assigned to the new user
   * @return the newly created {@link User} entity
   * @throws RuntimeException if a user with the given username already exists
   */
  public User createUser(final String username, final String rawPassword,
                         final String role) {
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

  /**
   * Retrieves a user by their username.
   *
   * @param username the username of the user to retrieve
   * @return an {@link Optional} containing the user if found, or empty if not
   */
  public Optional<User> findByUsername(final String username) {
    return userRepository.findByUsername(username);
  }
}
