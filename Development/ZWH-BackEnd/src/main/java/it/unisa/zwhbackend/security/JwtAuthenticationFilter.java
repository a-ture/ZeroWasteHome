package it.unisa.zwhbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro per l'autenticazione tramite JWT.
 *
 * <p>Questa classe intercetta tutte le richieste HTTP e verifica la presenza di un token JWT
 * nell'intestazione {@code Authorization}. Se il token è valido:
 *
 * <ul>
 *   <li>Estrae l'email dell'utente e i ruoli dal token.
 *   <li>Crea un oggetto {@code Authentication} con le informazioni dell'utente.
 *   <li>Imposta il contesto di sicurezza di Spring per la richiesta corrente.
 * </ul>
 *
 * <p>Estende {@code OncePerRequestFilter}, garantendo che il filtro venga eseguito una sola volta
 * per ogni richiesta.
 *
 * @author Marco Renella
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  /** Provider per la gestione e validazione dei token JWT. */
  private final JwtManualProvider jwtProvider;

  /**
   * Costruttore per {@code JwtAuthenticationFilter}.
   *
   * @param jwtProvider Il provider per la gestione e validazione dei token JWT.
   */
  public JwtAuthenticationFilter(JwtManualProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  /**
   * Intercetta e gestisce ogni richiesta HTTP per verificare l'autenticazione tramite JWT.
   *
   * <p>Estrae il token JWT dall'intestazione {@code Authorization}, lo valida, e se valido:
   *
   * <ul>
   *   <li>Estrae l'email dell'utente.
   *   <li>Estrae i ruoli dal token e li converte in oggetti {@code GrantedAuthority}.
   *   <li>Crea un oggetto {@code Authentication} e lo salva nel contesto di sicurezza.
   * </ul>
   *
   * @param request La richiesta HTTP in arrivo.
   * @param response La risposta HTTP in uscita.
   * @param chain La catena dei filtri HTTP.
   * @throws ServletException In caso di errori nella gestione della richiesta.
   * @throws IOException In caso di errori di I/O.
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    // Estrai il token JWT dall'intestazione Authorization
    String token = extractToken(request);

    // Se il token è valido, autentica l'utente
    if (token != null && jwtProvider.validateToken(token)) {
      String email = jwtProvider.getEmail(token); // Estrai l'email dal token

      // Recupera i ruoli dal token e li converte in oggetti GrantedAuthority
      List<String> roles = jwtProvider.getRoles(token);
      List<SimpleGrantedAuthority> authorities =
          roles.stream()
              .map(
                  role ->
                      new SimpleGrantedAuthority("ROLE_" + role)) // Aggiunge il prefisso "ROLE_"
              .collect(Collectors.toList());

      // Crea l'oggetto Authentication per Spring Security
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(email, null, authorities);

      // Imposta l'oggetto Authentication nel contesto di sicurezza
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Continua la catena dei filtri
    chain.doFilter(request, response);
  }

  /**
   * Estrae il token JWT dall'intestazione {@code Authorization}.
   *
   * <p>Il token deve essere presente nell'intestazione e deve iniziare con il prefisso {@code
   * Bearer }. Questo metodo rimuove il prefisso e restituisce solo il token.
   *
   * @param request La richiesta HTTP in arrivo.
   * @return Il token JWT se presente, altrimenti {@code null}.
   */
  private String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7); // Rimuove il prefisso "Bearer "
    }
    return null;
  }
}
