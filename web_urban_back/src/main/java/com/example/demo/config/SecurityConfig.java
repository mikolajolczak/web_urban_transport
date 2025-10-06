package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class responsible for defining the
 * application-wide Spring Security settings such as CORS, CSRF,
 * session management, request authorization, and custom filters.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * The custom JWT authentication filter responsible for
   * validating JWT tokens before the standard authentication
   * process.
   */
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * Constructs a new {@code SecurityConfig} instance.
   *
   * @param jwtAuthenticationFilterParam the JWT authentication filter
   *                                     to be injected by Spring
   */
  public SecurityConfig(
      final JwtAuthenticationFilter jwtAuthenticationFilterParam) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilterParam;
  }

  /**
   * Provides a {@link PasswordEncoder} bean using BCrypt hashing algorithm.
   *
   * @return a password encoder configured with BCrypt
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the {@link SecurityFilterChain} for the application.
   * This includes:
   * <ul>
   *   <li>Enabling CORS and disabling CSRF</li>
   *   <li>Configuring stateless session management</li>
   *   <li>Permitting access to authentication endpoints</li>
   *   <li>Requiring authentication for all other endpoints</li>
   *   <li>Adding the JWT authentication filter before the
   *       {@link UsernamePasswordAuthenticationFilter}</li>
   * </ul>
   *
   * @param http the {@link HttpSecurity} to modify
   * @return the configured {@link SecurityFilterChain}
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http)
      throws Exception {
    http
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/auth/login").permitAll()
            .requestMatchers("/api/auth/register").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class)
        .headers(headers -> headers
            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    return http.build();
  }
}
