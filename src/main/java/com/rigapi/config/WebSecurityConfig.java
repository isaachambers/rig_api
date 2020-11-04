package com.rigapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  @Bean("userDetailsServiceBean")
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("demo-user").password("{noop}user-password")
        .roles("USER", "ADMIN");
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/swagger-ui.html")
        .antMatchers("/docs")
        .antMatchers("/docs/json")
        .antMatchers("/h2-console/**")
        .antMatchers("/actuator/**")
        .antMatchers("/webjars/springfox-swagger-ui/**")
        .antMatchers("/swagger-resources/**")
        .antMatchers("/v2/api-docs");
  }
}
