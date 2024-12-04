package it.unisa.zwhbackend.service.gestioneUtenteStandard.autenticazione;

import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import it.unisa.zwhbackend.security.JwtManualProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio per la gestione dell'autenticazione degli utenti. Fornisce la logica
 * di business per autenticare un utente nel sistema. - Verifica la validità delle credenziali
 * dell'utente. - Genera un token JWT per gestire la sessione.
 *
 * @author Marco Renella
 */
@Service
public class GestioneAutenticazioneService implements AutenticazioneService {

  // Attributi per gestire l'autenticazione
  private PasswordEncoder
      passwordEncoder; // Utilizzato per verificare la validità delle password hashate
  private final UtenteRepository
      utenteRepository; // Interagisce con il database per trovare gli utenti
  private final JwtManualProvider jwtProvider; // Genera il token JWT per la sessione dell'utente

  /**
   * Costruttore che utilizza l'iniezione delle dipendenze.
   *
   * @param utenteRepository Repository per accedere agli utenti nel database
   * @param jwtProvider Classe per la generazione dei token JWT
   * @param passwordEncoder Classe per confrontare password in chiaro e hashate
   */
  @Autowired
  public GestioneAutenticazioneService(
      UtenteRepository utenteRepository,
      JwtManualProvider jwtProvider,
      PasswordEncoder passwordEncoder) {
    this.utenteRepository = utenteRepository;
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Metodo per autenticare un utente. - Verifica l'esistenza dell'utente tramite l'email. -
   * Confronta la password fornita con quella hashata salvata nel database. - Se l'autenticazione è
   * valida, genera un token JWT.
   *
   * @param email L'email dell'utente fornita durante il login
   * @param rawPassword La password fornita durante il login
   * @return Un token JWT generato per la sessione dell'utente
   * @throws Exception Se la generazione del token fallisce
   */
  @Override
  public String login(String email, String rawPassword) throws Exception {
    // Recupera l'utente dal database utilizzando l'email fornita
    Utente utente = utenteRepository.findByEmail(email);
    if (utente == null) {
      // Se l'utente non esiste, lancia un'eccezione con un messaggio di errore
      throw new IllegalArgumentException("Credenziali non valide");
    }

    // Verifica se la password fornita (rawPassword) corrisponde alla password hashata
    if (!passwordEncoder.matches(rawPassword, utente.getPassword())) {
      // Se la password non corrisponde, lancia un'eccezione con un messaggio di errore
      throw new IllegalArgumentException("Credenziali non valide");
    }

    // Se le credenziali sono valide, genera un token JWT per l'utente
    return jwtProvider.generateToken(utente.getEmail(), List.of("UTENTE"));
  }
}
