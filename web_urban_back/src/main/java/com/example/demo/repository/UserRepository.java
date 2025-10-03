package com.example.demo.repository;

import com.example.demo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  /**
   * Finds a user by their username.
   *
   * @param username the username of the user to find
   * @return an Optional containing the User if found, or empty if not found
   */
  Optional<User> findByUsername(String username);
}
