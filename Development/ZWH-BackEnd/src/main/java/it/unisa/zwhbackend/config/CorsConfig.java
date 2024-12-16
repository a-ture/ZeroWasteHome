package it.unisa.zwhbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configurazione CORS per il backend.
 *
 * <p>Questa classe configura le regole CORS (Cross-Origin Resource Sharing) per consentire la
 * comunicazione tra il frontend e il backend.
 *
 * @author Marco Renella
 */
@Configuration
public class CorsConfig {

  /**
   * Configura e restituisce un filtro CORS.
   *
   * <p>Questo metodo consente al backend di accettare richieste dal dominio specificato, con tutti
   * gli header e metodi HTTP consentiti. È utile per abilitare la comunicazione tra il frontend
   * Angular (localhost:4200) e il backend.
   *
   * @return un oggetto {@link CorsFilter} configurato con le regole definite.
   */
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();

    // Consente l'invio di cookie e credenziali nelle richieste
    config.setAllowCredentials(true);

    // Specifica l'origine consentita (frontend Angular)
    config.addAllowedOrigin("http://localhost:4200");

    // Consente tutti gli header nelle richieste
    config.addAllowedHeader("*");

    // Consente tutti i metodi HTTP (GET, POST, PUT, DELETE, ecc.)
    config.addAllowedMethod("*");

    // Applica la configurazione a tutti gli endpoint del backend
    source.registerCorsConfiguration("/**", config);

    // Restituisce il filtro configurato
    return new CorsFilter(source);
  }
}
