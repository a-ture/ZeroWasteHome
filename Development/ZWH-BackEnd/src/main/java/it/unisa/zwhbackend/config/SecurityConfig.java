package it.unisa.zwhbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe di configurazione per la sicurezza dell'applicazione. Fornisce la configurazione della
 * sicurezza tramite Spring Security.
 *
 * @author Marco Renella
 */
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // Disabilita la protezione CSRF (momentaneo)
        .authorizeHttpRequests(
            auth ->
                auth.anyRequest()
                    .permitAll()); // Permette l'accesso a tutte le richieste (momentaneo)
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
