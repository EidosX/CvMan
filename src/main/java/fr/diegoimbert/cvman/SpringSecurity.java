package fr.diegoimbert.cvman;

import jakarta.annotation.PostConstruct;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.model.User;

@EnableWebSecurity
@Component
@Configuration
public class SpringSecurity {
  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() {
    var rootUser = new User(
        null, "Root", "One", null, "rootone@email.fr",
        null, new Date(),
        "I am the root user. I am nothing special," +
            "except I am the first ever user of this website",
        passwordEncoder.encode("root"),
        User.Role.VISITOR, null);
    userRepository.save(rootUser);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());

    return http
        .httpBasic(basic -> basic.disable())
        // .authorizeHttpRequests((requests) -> requests
        // .requestMatchers("/", "/api/**").permitAll()
        // .anyRequest().authenticated())
        // .formLogin((form) -> form.permitAll())
        // .logout((logout) -> logout.permitAll())
        .build();
  }

  @Autowired
  UserDetailsService userDetailsService;

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }
}
