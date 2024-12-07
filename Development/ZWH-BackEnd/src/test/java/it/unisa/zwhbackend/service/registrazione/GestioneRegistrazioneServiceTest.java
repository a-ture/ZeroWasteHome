package it.unisa.zwhbackend.service.registrazione;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import it.unisa.zwhbackend.service.registazione.GestioneRegistrazioneService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe di test per il servizio di gestione della registrazione degli utenti.
 *
 * <p>Questa classe esegue solo determinati test sui metodi del servizio {@code
 * GestioneRegistrazioneService}
 */
@SpringBootTest
class GestioneRegistrazioneServiceTest {

  /** Repository per la gestione degli utenti. */
  private UtenteRepository utenteRepository;

  /** Encoder per la gestione delle password sicure. */
  private PasswordEncoder passwordEncoder;

  /** Servizio per la gestione della registrazione degli utenti. */
  private GestioneRegistrazioneService gestioneRegistrazioneService;

  /** Validator per la validazione delle informazioni dell'utente. */
  private Validator validator;

  /**
   * Configura l'ambiente di test prima dell'esecuzione di ogni test.
   *
   * <p>Inizializza i mock degli oggetti necessari e il servizio di registrazione. Crea un {@code
   * Validator} per validare gli oggetti {@code Utente}.
   */
  @BeforeEach
  void setUp() {
    utenteRepository = mock(UtenteRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    gestioneRegistrazioneService =
        new GestioneRegistrazioneService(utenteRepository, passwordEncoder);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Verifica che venga sollevata una violazione di validazione quando l'email dell'utente non è
   * valida.
   */
  @Test
  void testEmailNonValida_TC_GUS_RU_01() {
    Utente utente = creaUtente();
    utente.setEmail("email_invalida");

    Set<ConstraintViolation<Utente>> violazioni = validator.validate(utente);

    assertEquals(1, violazioni.size());
    assertEquals("Inserisci un'email valida", violazioni.iterator().next().getMessage());
  }

  /**
   * Verifica che venga sollevata un'eccezione quando si tenta di registrare un utente con un'email
   * già registrata.
   */
  @Test
  void testEmailGiaPresente_TC_GUS_RU_02() {
    Utente utente = creaUtente();
    when(utenteRepository.existsByEmail(utente.getEmail())).thenReturn(true);

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> gestioneRegistrazioneService.registrazione(utente));

    assertEquals("Email già registrata", exception.getMessage());
  }

  /**
   * Verifica che la registrazione dell'utente avvenga correttamente quando l'email non è già
   * registrata, e la password venga codificata correttamente.
   */
  @Test
  void testRegistrazioneEffettuata_TC_GUS_RU_03() {
    Utente utente = creaUtente();
    when(utenteRepository.existsByEmail(utente.getEmail())).thenReturn(false);
    when(passwordEncoder.encode(utente.getPassword())).thenReturn("encodedPassword");
    when(utenteRepository.save(utente)).thenReturn(utente);

    Utente risultato = gestioneRegistrazioneService.registrazione(utente);

    assertNotNull(risultato);
    assertEquals("encodedPassword", risultato.getPassword());
    assertEquals(utente.getEmail(), risultato.getEmail());
  }

  /**
   * Verifica che venga sollevata una violazione di validazione quando la password dell'utente è
   * troppo corta.
   */
  @Test
  void testPasswordTroppoCorta_TC_GUS_RU_04() {
    Utente utente = creaUtente();
    utente.setPassword("short");

    Set<ConstraintViolation<Utente>> violazioni = validator.validate(utente);

    assertEquals(1, violazioni.size());
    assertEquals(
        "La password deve avere almeno 8 caratteri", violazioni.iterator().next().getMessage());
  }

  /**
   * Crea un utente di esempio con dati validi per i test.
   *
   * @return Un oggetto {@code Utente} con valori di esempio.
   */
  private Utente creaUtente() {
    Utente utente = new Utente();
    utente.setName("alessia");
    utente.setEmail("alessiature@gmail.com");
    utente.setPassword("Password$123");
    return utente;
  }
}
