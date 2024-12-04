package it.unisa.zwhbackend.config;

import it.unisa.zwhbackend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configurazione della sicurezza per l'applicazione.
 *
 * <p>Questa classe utilizza Spring Security per definire le regole di sicurezza dell'applicazione,
 * inclusi i filtri per l'autenticazione tramite JWT e le autorizzazioni per l'accesso agli
 * endpoint.
 *
 * <p>Annotata con {@code @Configuration}, indica a Spring che questa classe fornisce configurazioni
 * necessarie per il contesto di sicurezza. Contiene metodi per:
 *
 * <ul>
 *   <li>Configurare la catena di filtri di sicurezza tramite {@code SecurityFilterChain}.
 *   <li>Definire un {@code PasswordEncoder} per la codifica delle password.
 * </ul>
 *
 * @author Marco Renella
 */
@Configuration
public class SecurityConfig {

  /** Filtro per l'autenticazione JWT. * */
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * Costruttore per {@code SecurityConfig}.
   *
   * @param jwtAuthenticationFilter Il filtro di autenticazione JWT
   */
  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  /**
   * Configura la catena dei filtri di sicurezza e le regole di accesso agli endpoint.
   *
   * <p>Disabilita CSRF per le API stateless, definisce i permessi per i vari endpoint e aggiunge il
   * filtro di autenticazione JWT.
   *
   * @param http Configuratore di sicurezza HTTP fornito da Spring Security.
   * @return La configurazione della catena di filtri.
   * @throws Exception Se si verifica un errore durante la configurazione.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Disabilita CSRF (necessario per API stateless)
        .csrf(csrf -> csrf.disable())

        // Configura le autorizzazioni per i vari endpoint
        .authorizeHttpRequests(
            auth ->
                auth
                    // Endpoint pubblici, accessibili senza autenticazione
                    .requestMatchers("/api/public/**")
                    .permitAll()

                    // Endpoint per i gestori, richiede il ruolo GESTORE_COMMUNITY o
                    // GESTORE_PAGAMENTI
                    .requestMatchers("/api/gestore/**")
                    .hasAnyRole("GESTORE_COMMUNITY", "GESTORE_PAGAMENTI")

                    // Endpoint per utenti registrati, richiede il ruolo UTENTE o il ruolo gestore
                    .requestMatchers("/api/utente/**")
                    .hasAnyRole("UTENTE", "GESTORE_COMMUNITY", "GESTORE_PAGAMENTI")

                    // Tutti gli altri endpoint sono attualmente accessibili (in futuro: modificare
                    // per richiedere autenticazione)
                    .anyRequest()
                    .permitAll())

        // Aggiunge il filtro JWT per gestire l'autenticazione
        .addFilterBefore(
            jwtAuthenticationFilter,
            org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
                .class);

    return http.build();
  }

  /**
   * Bean per il codificatore delle password.
   *
   * <p>Utilizza BCrypt per garantire una maggiore sicurezza delle password.
   *
   * @return L'implementazione di {@code PasswordEncoder}.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
