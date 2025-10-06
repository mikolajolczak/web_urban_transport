package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Spring Boot demo application.
 */
@SpringBootApplication
public class DemoApplication {

  private DemoApplication() {
  }

  /**
   * Entry point of the Spring Boot application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(final String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
