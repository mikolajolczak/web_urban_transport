package com.example.demo.security;

import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A filter that intercepts incoming HTTP requests to perform JWT-based
 * authentication.
 *
 * <p>This filter extracts the JWT token from a cookie named {@code jwt-token},
 * validates it,
 * and, if valid, sets the {@link SecurityContextHolder} with an
 * authentication object
 * containing the username and role extracted from the token.
 * </p>
 *
 * <p>This class extends {@link OncePerRequestFilter}, ensuring that the filter
 * is executed
 * once per request.
 * </p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  /**
   * The name of the cookie that stores the JWT token.
   */
  private static final String JWT_COOKIE_NAME = "jwt-token";
  /**
   * The service responsible for JWT creation, parsing, and validation.
   */
  private final JwtService jwtService;

  /**
   * Constructs a new {@code JwtAuthenticationFilter} with the specified
   * {@link JwtService}.
   *
   * @param jwtServiceParam the {@link JwtService} used to validate and parse
   *                        JWT tokens
   */
  public JwtAuthenticationFilter(final JwtService jwtServiceParam) {
    this.jwtService = jwtServiceParam;
  }

  /**
   * Extracts the JWT token from the request cookie, validates it, and sets
   * the authentication
   * in the {@link SecurityContextHolder} if the token is valid.
   *
   * @param request     the {@link HttpServletRequest} object
   * @param response    the {@link HttpServletResponse} object
   * @param filterChain the {@link FilterChain} for invoking the next filter
   * @throws ServletException if an error occurs during filtering
   * @throws IOException      if an I/O error occurs during filtering
   */
  @Override
  protected void doFilterInternal(
      @NonNull final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain
  ) throws ServletException, IOException {

    String jwt = null;

    if (request.getCookies() != null) {
      jwt = Arrays.stream(request.getCookies())
          .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
          .map(Cookie::getValue)
          .findFirst()
          .orElse(null);
    }

    if (jwt != null) {
      try {
        String username = jwtService.extractUsername(jwt);

        if (username != null
            && SecurityContextHolder.getContext().getAuthentication() == null) {
          if (jwtService.isTokenValid(jwt, username)) {
            String role = jwtService.extractRole(jwt);
            SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singletonList(authority)
                );

            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }
      } catch (Exception error) {
        System.err.println(error.getMessage());
      }
    }

    filterChain.doFilter(request, response);
  }
}
