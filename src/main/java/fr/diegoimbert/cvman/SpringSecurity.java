package fr.diegoimbert.cvman;

import jakarta.annotation.PostConstruct;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import fr.diegoimbert.cvman.lib.auth.JwtAuthorizationFilter;
import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.model.User;

@EnableWebSecurity
@Component
@Configuration
public class SpringSecurity {
  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtAuthorizationFilter jwtAuthorizationFilter;

  @PostConstruct
  public void init() {
    var rootUser = new User(
        null, "Root", "One", null, "root@eidos.cc",
        null, new Date(),
        "I am the root user. I am nothing special," +
            "except I am the first ever user of this website",
        passwordEncoder.encode("rootroot"),
        User.Role.ADMIN, null);
    userRepository.save(rootUser);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
    return http
        .csrf(c -> c.disable())
        .httpBasic(basic -> basic.disable())
        .authorizeHttpRequests((requests) -> {
          try {
            requests
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,
                    "/api/auth/login"))
                .permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,
                    "/api/auth/signup"))
                .permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/user/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).authenticated().anyRequest().permitAll();
          } catch (Exception e) {
            e.printStackTrace();
          }
        })
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder)
      throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http
        .getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);
    return authenticationManagerBuilder.build();
  }
}
