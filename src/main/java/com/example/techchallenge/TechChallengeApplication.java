package com.example.techchallenge;

import com.example.techchallenge.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class TechChallengeApplication {

  public static void main(String[] args) {
    SpringApplication.run(TechChallengeApplication.class, args);
  }

  @EnableWebSecurity
  @Configuration
  static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf()
          .disable()
          .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
          .authorizeRequests()
          .antMatchers(HttpMethod.POST, "/auth", "/institutions")
          .permitAll()
          .anyRequest()
          .authenticated();
    }
  }
}
