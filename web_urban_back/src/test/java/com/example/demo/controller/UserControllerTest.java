package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

  private UserService userService;
  private JwtService jwtService;
  private UserController userController;
  private HttpServletResponse response;
  private HttpServletRequest request;

  @BeforeEach
  void setUp() {
    userService = mock(UserService.class);
    jwtService = mock(JwtService.class);
    response = mock(HttpServletResponse.class);
    request = mock(HttpServletRequest.class);
    userController = new UserController(userService, jwtService);
  }

  @Test
  void login_Successful() {
    User user = new User();
    user.setUsername("testUser");
    user.setRole("USER");
    Employee employee = new Employee();
    employee.setId(123);
    user.setEmployee(employee);

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("testUser");
    loginRequest.setPassword("pass");

    when(userService.login("testUser", "pass")).thenReturn(user);
    when(jwtService.generateToken("testUser", "USER", 123)).thenReturn(
        "token123");

    ResponseEntity<LoginResponse> responseEntity =
        userController.login(loginRequest, response);

    ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
    verify(response).addCookie(cookieCaptor.capture());
    Cookie cookie = cookieCaptor.getValue();

    assertEquals("jwt-token", cookie.getName());
    assertEquals("token123", cookie.getValue());
    assertTrue(cookie.isHttpOnly());
    assertEquals(24 * 60 * 60, cookie.getMaxAge());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    assertEquals("USER", responseEntity.getBody().getRole());
    assertEquals(123, responseEntity.getBody().getEmployeeId());
    assertEquals("testUser", responseEntity.getBody().getUsername());
  }

  @Test
  void login_Failure() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("testUser");
    loginRequest.setPassword("wrongPass");

    when(userService.login("testUser", "wrongPass")).thenThrow(
        new RuntimeException());

    ResponseEntity<LoginResponse> responseEntity =
        userController.login(loginRequest, response);

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @Test
  void logout_SetsCookieToExpire() {
    ResponseEntity<Void> responseEntity = userController.logout(response);

    ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
    verify(response).addCookie(cookieCaptor.capture());
    Cookie cookie = cookieCaptor.getValue();

    assertEquals("jwt-token", cookie.getName());
    assertNull(cookie.getValue());
    assertEquals(0, cookie.getMaxAge());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void validateToken_Valid() {
    Cookie cookie = new Cookie("jwt-token", "token123");
    when(request.getCookies()).thenReturn(new Cookie[]{cookie});
    when(jwtService.extractUsername("token123")).thenReturn("testUser");
    when(jwtService.isTokenValid("token123", "testUser")).thenReturn(true);

    ResponseEntity<Boolean> responseEntity =
        userController.validateToken(request);

    assertEquals(Boolean.TRUE, responseEntity.getBody());
  }

  @Test
  void validateToken_Invalid() {
    Cookie cookie = new Cookie("jwt-token", "token123");
    when(request.getCookies()).thenReturn(new Cookie[]{cookie});
    when(jwtService.extractUsername("token123")).thenReturn("testUser");
    when(jwtService.isTokenValid("token123", "testUser")).thenReturn(false);

    ResponseEntity<Boolean> responseEntity =
        userController.validateToken(request);

    assertNotEquals(Boolean.TRUE, responseEntity.getBody());
  }

  @Test
  void validateToken_NoCookie() {
    when(request.getCookies()).thenReturn(null);
    ResponseEntity<Boolean> responseEntity =
        userController.validateToken(request);
    assertNotEquals(Boolean.TRUE, responseEntity.getBody());
  }

  @Test
  void getCurrentUser_ValidTokenWithEmployee() {
    User user = new User();
    user.setUsername("user1");
    user.setRole("ADMIN");
    Employee employee = new Employee();
    employee.setId(10);
    user.setEmployee(employee);

    Cookie cookie = new Cookie("jwt-token", "token123");
    when(request.getCookies()).thenReturn(new Cookie[]{cookie});
    when(jwtService.extractUsername("token123")).thenReturn("user1");
    when(jwtService.isTokenValid("token123", "user1")).thenReturn(true);
    when(userService.findByUsername("user1")).thenReturn(Optional.of(user));

    ResponseEntity<LoginResponse> responseEntity =
        userController.getCurrentUser(request);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    assertEquals("ADMIN", responseEntity.getBody().getRole());
    assertEquals(10, responseEntity.getBody().getEmployeeId());
    assertEquals("user1", responseEntity.getBody().getUsername());
  }

  @Test
  void getCurrentUser_ValidTokenWithoutEmployee() {
    User user = new User();
    user.setUsername("user2");
    user.setRole("USER");
    user.setEmployee(null);

    Cookie cookie = new Cookie("jwt-token", "token123");
    when(request.getCookies()).thenReturn(new Cookie[]{cookie});
    when(jwtService.extractUsername("token123")).thenReturn("user2");
    when(jwtService.isTokenValid("token123", "user2")).thenReturn(true);
    when(userService.findByUsername("user2")).thenReturn(Optional.of(user));

    ResponseEntity<LoginResponse> responseEntity =
        userController.getCurrentUser(request);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertNotNull(responseEntity.getBody());
    assertEquals("USER", responseEntity.getBody().getRole());
    assertEquals(-1, responseEntity.getBody().getEmployeeId());
    assertEquals("user2", responseEntity.getBody().getUsername());
  }

  @Test
  void getCurrentUser_InvalidToken() {
    Cookie cookie = new Cookie("jwt-token", "token123");
    when(request.getCookies()).thenReturn(new Cookie[]{cookie});
    when(jwtService.extractUsername("token123")).thenReturn("user1");
    when(jwtService.isTokenValid("token123", "user1")).thenReturn(false);

    ResponseEntity<LoginResponse> responseEntity =
        userController.getCurrentUser(request);

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @Test
  void getCurrentUser_NoCookie() {
    when(request.getCookies()).thenReturn(null);
    ResponseEntity<LoginResponse> responseEntity =
        userController.getCurrentUser(request);
    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @Test
  void register_Success() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setUsername("newUser");
    registerRequest.setPassword("pass");
    registerRequest.setRole("USER");

    when(userService.createUser("newUser", "pass", "USER")).thenReturn(
        new User());

    ResponseEntity<String> responseEntity =
        userController.register(registerRequest);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  void register_Failure() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setUsername("newUser");
    registerRequest.setPassword("pass");
    registerRequest.setRole("USER");

    when(userService.createUser("newUser", "pass", "USER")).thenThrow(
        new RuntimeException());

    ResponseEntity<String> responseEntity =
        userController.register(registerRequest);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }
}
