package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  private User activeUser;
  private User inactiveUser;

  private AutoCloseable mocks;

  @BeforeEach
  void setUp() {
    mocks = MockitoAnnotations.openMocks(this);

    activeUser = new User();
    activeUser.setUsername("john");
    activeUser.setPassword("encodedPassword");
    activeUser.setRole("USER");
    activeUser.setActive(true);

    inactiveUser = new User();
    inactiveUser.setUsername("inactive");
    inactiveUser.setPassword("encodedPassword");
    inactiveUser.setRole("USER");
    inactiveUser.setActive(false);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void login_Successful() {
    when(userRepository.findByUsername("john")).thenReturn(
        Optional.of(activeUser));
    when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(
        true);

    User result = userService.login("john", "rawPassword");

    assertEquals("john", result.getUsername());
    assertTrue(result.isActive());
    verify(userRepository).findByUsername("john");
    verify(passwordEncoder).matches("rawPassword", "encodedPassword");
  }

  @Test
  void login_UserNotFound_ThrowsException() {
    when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        userService.login("unknown", "password")
    );

    assertEquals("Username not found", exception.getMessage());
    verify(userRepository).findByUsername("unknown");
    verifyNoInteractions(passwordEncoder);
  }

  @Test
  void login_UserInactive_ThrowsException() {
    when(userRepository.findByUsername("inactive")).thenReturn(
        Optional.of(inactiveUser));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        userService.login("inactive", "password")
    );

    assertEquals("User account is inactive", exception.getMessage());
    verify(userRepository).findByUsername("inactive");
    verifyNoInteractions(passwordEncoder);
  }

  @Test
  void login_InvalidPassword_ThrowsException() {
    when(userRepository.findByUsername("john")).thenReturn(
        Optional.of(activeUser));
    when(
        passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(
        false);

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        userService.login("john", "wrongPassword")
    );

    assertEquals("Invalid credentials", exception.getMessage());
    verify(userRepository).findByUsername("john");
    verify(passwordEncoder).matches("wrongPassword", "encodedPassword");
  }

  @Test
  void createUser_Successful() {
    when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
    when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenAnswer(
        invocation -> invocation.getArgument(0));

    User result = userService.createUser("newUser", "password", "ADMIN");

    assertEquals("newUser", result.getUsername());
    assertEquals("encodedPassword", result.getPassword());
    assertEquals("ADMIN", result.getRole());
    assertTrue(result.isActive());

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());
    assertEquals("newUser", userCaptor.getValue().getUsername());
  }

  @Test
  void createUser_UsernameExists_ThrowsException() {
    when(userRepository.findByUsername("john")).thenReturn(
        Optional.of(activeUser));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        userService.createUser("john", "password", "USER")
    );

    assertEquals("Username already exists", exception.getMessage());
    verify(userRepository).findByUsername("john");
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void findByUsername_UserExists() {
    when(userRepository.findByUsername("john")).thenReturn(
        Optional.of(activeUser));

    Optional<User> result = userService.findByUsername("john");

    assertTrue(result.isPresent());
    assertEquals("john", result.get().getUsername());
    verify(userRepository).findByUsername("john");
  }

  @Test
  void findByUsername_UserDoesNotExist() {
    when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

    Optional<User> result = userService.findByUsername("unknown");

    assertFalse(result.isPresent());
    verify(userRepository).findByUsername("unknown");
  }
}
