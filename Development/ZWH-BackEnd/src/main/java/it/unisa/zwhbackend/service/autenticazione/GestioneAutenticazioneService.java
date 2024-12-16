package it.unisa.zwhbackend.service.autenticazione;

import it.unisa.zwhbackend.annotations.ExcludeGeneratedFromCodeCoverage;
import it.unisa.zwhbackend.model.entity.GestoreCommunity;
import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.GestoreCommunityRepository;
import it.unisa.zwhbackend.model.repository.GestorePagamentoRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import it.unisa.zwhbackend.security.JwtManualProvider;
import java.util.List;
import java.util.Optional;
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
  private final GestoreCommunityRepository
      gestoreCommunityRepository; // Interagisce con il database per trovare i gestori della
  // community
  private final GestorePagamentoRepository
      gestorePagamentoRepository; // Interagisce con il database per trovare i gestori della
  // community
  private final JwtManualProvider jwtProvider; // Genera il token JWT per la sessione dell'utente

  /**
   * Costruttore che utilizza l'iniezione delle dipendenze.
   *
   * @param utenteRepository Repository per accedere agli utenti nel database
   * @param gestoreCommunityRepository Repository per accedere ai gestori della community nel
   *     database
   * @param gestorePagamentoRepository Repository per accedere ai gestori dei pagamenti nel database
   * @param jwtProvider Classe per la generazione dei token JWT
   * @param passwordEncoder Classe per confrontare password in chiaro e hashate
   */
  @Autowired
  public GestioneAutenticazioneService(
      UtenteRepository utenteRepository,
      JwtManualProvider jwtProvider,
      PasswordEncoder passwordEncoder,
      GestoreCommunityRepository gestoreCommunityRepository,
      GestorePagamentoRepository gestorePagamentoRepository) {
    this.utenteRepository = utenteRepository;
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
    this.gestoreCommunityRepository = gestoreCommunityRepository;
    this.gestorePagamentoRepository = gestorePagamentoRepository;
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

  /**
   * Gestisce il login per i gestori, cercando il gestore sia nella tabella GestoreCommunity che in
   * GestorePagamento. Se uno dei due esiste, verifica le credenziali e genera un token JWT con il
   * ruolo corrispondente. Se entrambi esistono, assegna entrambi i ruoli.
   *
   * @param email L'email del gestore.
   * @param rawPassword La password in chiaro fornita per l'autenticazione.
   * @return Un token JWT generato per il gestore autenticato con i ruoli corrispondenti.
   * @throws IllegalArgumentException Se il gestore non esiste o le credenziali non sono valide.
   */
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public String loginAmministrativo(String email, String rawPassword) throws Exception {
    // Recupera il gestore dalla tabella GestoreCommunity
    GestoreCommunity gestoreCommunity = gestoreCommunityRepository.findByEmail(email);
    // Recupera il gestore dalla tabella GestorePagamento
    Optional<GestorePagamento> gestorePagamento = gestorePagamentoRepository.findByEmail(email);

    if (gestoreCommunity == null && gestorePagamento.isEmpty()) {
      // Se entrambi i gestori non esistono, lancia un'eccezione
      throw new IllegalArgumentException("Credenziali non valide");
    }

    if (gestoreCommunity != null) {
      // Verifica la password per GestoreCommunity
      if (passwordEncoder.matches(rawPassword, gestoreCommunity.getPassword())) {
        // Se GestorePagamento è null, restituisce il token con ruolo GESTORE_COMMUNITY
        if (gestorePagamento.isEmpty()) {
          return jwtProvider.generateToken(
              gestoreCommunity.getEmail(), List.of("GESTORE_COMMUNITY"));
        }
      } else {
        // Se la password non corrisponde per GestoreCommunity
        throw new IllegalArgumentException("Credenziali non valide");
      }
    }

    // Verifica la password per GestorePagamento
    if (passwordEncoder.matches(rawPassword, gestorePagamento.get().getPassword())) {
      // Se GestoreCommunity è null, restituisce il token con ruolo GESTORE_PAGAMENTO
      if (gestoreCommunity == null) {
        return jwtProvider.generateToken(
            gestorePagamento.get().getEmail(), List.of("GESTORE_PAGAMENTI"));
      }
    } else {
      // Se la password non corrisponde per GestorePagamento
      throw new IllegalArgumentException("Credenziali non valide");
    }

    // Se entrambi i gestori esistono e la password corrisponde, assegna entrambi i ruoli
    return jwtProvider.generateToken(email, List.of("GESTORE_COMMUNITY", "GESTORE_PAGAMENTI"));
  }
}
