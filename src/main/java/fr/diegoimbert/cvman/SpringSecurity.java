package fr.diegoimbert.cvman;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import fr.diegoimbert.cvman.lib.dao.UserRepository;

@EnableWebSecurity
@Component
@Configuration
public class SpringSecurity {
  @Autowired
  UserRepository userRepo;

  @PostConstruct
  public void init() {
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());

    return http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/", "/api/**").permitAll()
            .anyRequest().authenticated())
        .formLogin((form) -> form.permitAll())
        .logout((logout) -> logout.permitAll()).build();

  }

  @Autowired
  UserDetailsService userDetailsService;

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
