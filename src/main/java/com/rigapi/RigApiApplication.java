package com.rigapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class RigApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(RigApiApplication.class, args);
  }

}