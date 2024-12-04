package it.unisa.zwhbackend.service.gestioneUtenteStandard.autenticazione;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import it.unisa.zwhbackend.security.JwtManualProvider;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe di test per {@link GestioneAutenticazioneService}. Contiene i test unitari per verificare
 * il comportamento dei metodi relativi all'autenticazione.
 *
 * @author Marco Renella
 */
class GestioneAutenticazioneServiceTest {

  private UtenteRepository utenteRepository;
  private PasswordEncoder passwordEncoder;
  private JwtManualProvider jwtProvider;
  private GestioneAutenticazioneService gestioneAutenticazioneService;

  /** Inizializza i mock e l'istanza del servizio da testare prima di ogni test. */
  @BeforeEach
  void setUp() {
    utenteRepository = mock(UtenteRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    jwtProvider = mock(JwtManualProvider.class);
    gestioneAutenticazioneService =
        new GestioneAutenticazioneService(utenteRepository, jwtProvider, passwordEncoder);
  }

  /**
   * Test del metodo {@link GestioneAutenticazioneService#login(String, String)} con un'email non
   * registrata. Caso di test: TC_GUS_AU_01. Verifica che venga lanciata un'eccezione con il
   * messaggio "Credenziali non valide".
   */
  @Test
  void testLoginEmailNonRegistrata_TC_GUS_AU_01() {
    String email = "nonexistent@example.com";
    String password = "Password$123";

    when(utenteRepository.findByEmail(email)).thenReturn(null);

    Exception exception =
        assertThrows(
            Exception.class,
            () -> {
              gestioneAutenticazioneService.login(email, password);
            });

    assertEquals("Credenziali non valide", exception.getMessage());
  }

  /**
   * Test del metodo {@link GestioneAutenticazioneService#login(String, String)} con una password
   * errata. Caso di test: TC_GUS_AU_02. Verifica che venga lanciata un'eccezione con il messaggio
   * "Credenziali non valide".
   */
  @Test
  void testLoginPasswordErrata_TC_GUS_AU_02() {
    String email = "user@example.com";
    String rawPassword = "Password$123";
    Utente utente = creaUtente();

    when(utenteRepository.findByEmail(email)).thenReturn(utente);
    when(passwordEncoder.matches(rawPassword, utente.getPassword())).thenReturn(false);

    Exception exception =
        assertThrows(
            Exception.class,
            () -> {
              gestioneAutenticazioneService.login(email, rawPassword);
            });

    assertEquals("Credenziali non valide", exception.getMessage());
  }

  /**
   * Test del metodo {@link GestioneAutenticazioneService#login(String, String)} con credenziali
   * valide. Caso di test: TC_GUS_AU_03. Verifica che venga restituito un token JWT non nullo e che
   * corrisponda a quello atteso.
   *
   * @throws Exception se il login non viene eseguito correttamente.
   */
  @Test
  void testLoginSuccesso_TC_GUS_AU_03() throws Exception {
    String email = "user@example.com";
    String rawPassword = "Password$123";
    Utente utente = creaUtente();
    String token = "jwtToken";

    when(utenteRepository.findByEmail(email)).thenReturn(utente);
    when(passwordEncoder.matches(rawPassword, utente.getPassword())).thenReturn(true);
    when(jwtProvider.generateToken(email, List.of("UTENTE"))).thenReturn(token);

    String risultato = gestioneAutenticazioneService.login(email, rawPassword);

    assertNotNull(risultato);
    assertEquals(token, risultato);
  }

  /**
   * Metodo di supporto per creare un'istanza di {@link Utente} con dati predefiniti (corretti) per
   * i test.
   *
   * @return un'istanza di {@link Utente}.
   */
  private Utente creaUtente() {
    Utente utente = new Utente();
    utente.setEmail("user@example.com");
    utente.setPassword("$10$XhiUIcwYOI6cCcF0wh3SUeCNlM/G7RyIYJLsxxOdtlNyWi8.cYItu");
    return utente;
  }
}
