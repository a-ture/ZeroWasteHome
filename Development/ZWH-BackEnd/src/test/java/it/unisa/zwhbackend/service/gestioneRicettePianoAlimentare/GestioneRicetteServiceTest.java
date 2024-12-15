package it.unisa.zwhbackend.service.gestioneRicettePianoAlimentare;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.Ricetta;
import it.unisa.zwhbackend.model.enums.CategoriaRicetta;
import it.unisa.zwhbackend.model.repository.RicettaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Classe di test per il servizio GestioneRicetteService.
 *
 * <p>Questa classe contiene test case per verificare la validità delle operazioni di aggiunta e
 * validazione di ricette. Gli scenari di test includono casi di successo e di errore dovuti a
 * violazioni delle regole di validazione.
 *
 * @author Anna Tagliamonte
 */
@SpringBootTest
class GestioneRicetteServiceTest {

  private RicettaRepository ricettaRepository;
  private GestioneRicetteService gestioneRicetteService;
  private Validator validator;

  /**
   * Configurazione iniziale per i test.
   *
   * <p>Inizializza i mock per il repository e il validatore.
   */
  @BeforeEach
  void setUp() {
    ricettaRepository = mock(RicettaRepository.class);
    gestioneRicetteService = new GestioneRicetteService(ricettaRepository, null);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Testa la creazione di una ricetta valida.
   *
   * <p>Simula l'aggiunta di una ricetta corretta, verificando che venga salvata senza errori e che
   * i dati restituiti siano coerenti con quelli inseriti.
   */
  @Test
  void testTC_GCCR_CRC_01() {
    Ricetta ricetta = creaRicetta(); // Ricetta corretta

    when(ricettaRepository.save(ricetta)).thenReturn(ricetta);

    Ricetta risultato = gestioneRicetteService.aggiungiRicetta(ricetta);

    assertNotNull(risultato);
    assertEquals("Torta al Cioccolato", risultato.getNome());
    assertEquals(ricetta, risultato);
  }

  /**
   * Testa la validazione per una ricetta con nome nullo.
   *
   * <p>Verifica che il sistema rilevi un errore quando il nome della ricetta non è specificato.
   */
  @Test
  void testTC_GCCR_CRC_02() {
    Ricetta ricetta = creaRicetta();
    ricetta.setNome(null); // Nome non valido

    Set<ConstraintViolation<Ricetta>> violazioni = validator.validate(ricetta);

    assertEquals(1, violazioni.size());
    assertEquals(
        "Il campo 'Nome della ricetta' è obbligatorio", violazioni.iterator().next().getMessage());
  }

  /**
   * Testa la validazione per un nome troppo lungo.
   *
   * <p>Verifica che venga generato un errore quando il nome della ricetta supera i 100 caratteri.
   */
  @Test
  void testTC_GCCR_CRC_03() {
    Ricetta ricetta = creaRicetta();
    ricetta.setNome("T".repeat(101)); // Nome lungo 101 caratteri

    Set<ConstraintViolation<Ricetta>> violazioni = validator.validate(ricetta);

    assertEquals(1, violazioni.size());
    ConstraintViolation<Ricetta> violazione = violazioni.iterator().next();
    assertEquals(
        "Il nome della ricetta deve essere tra 1 e 100 caratteri", violazione.getMessage());
  }

  /**
   * Testa il comportamento del sistema quando la lista degli ingredienti è vuota.
   *
   * <p>Verifica che venga generata una violazione di validazione con un messaggio d'errore
   * specifico, poiché l'elenco degli ingredienti non può essere vuoto.
   */
  @Test
  void testTC_GCCR_CRC_04() {
    Ricetta ricetta = creaRicetta();
    ricetta.setIngredienti(List.of()); // Elenco ingredienti vuoto

    Set<ConstraintViolation<Ricetta>> violazioni = validator.validate(ricetta);

    // Controlla che ci sia una violazione
    assertFalse(
        violazioni.isEmpty(),
        "La validazione avrebbe dovuto fallire per l'elenco ingredienti vuoto");

    // Controlla il messaggio di errore specifico
    String messaggioErrore = violazioni.iterator().next().getMessage();
    assertEquals("L'elenco ingredienti non può essere vuoto", messaggioErrore);
  }

  /**
   * Testa la validazione per le istruzioni mancanti.
   *
   * <p>Verifica che venga generato un errore quando il campo istruzioni è nullo.
   */
  @Test
  void testTC_GCCR_CRC_05() {
    Ricetta ricetta = creaRicetta();
    ricetta.setIstruzioni(null); // Istruzioni mancanti

    Set<ConstraintViolation<Ricetta>> violazioni = validator.validate(ricetta);

    assertEquals(1, violazioni.size());
    assertEquals("Il campo 'Istruzioni' è obbligatorio", violazioni.iterator().next().getMessage());
  }

  /**
   * Testa la validazione per una categoria nulla.
   *
   * <p>Verifica che il sistema generi un errore quando la categoria non è specificata.
   */
  @Test
  void testTC_GCCR_CRC_06() {
    Ricetta ricetta = creaRicetta();
    ricetta.setCategoria(null); // Categoria non valida

    Set<ConstraintViolation<Ricetta>> violazioni = validator.validate(ricetta);

    assertFalse(violazioni.isEmpty(), "La validazione avrebbe dovuto fallire per categoria nulla");
    String messaggioErrore = violazioni.iterator().next().getMessage();
    assertEquals("Seleziona una categoria valida per la ricetta", messaggioErrore);
  }

  /**
   * Testa la validazione per un formato immagine non valido.
   *
   * <p>Verifica che il sistema generi un errore se il formato dell'immagine non è JPG o PNG.
   */
  @Test
  void testTC_GCCR_CRC_07() {
    Ricetta ricetta = creaRicetta();
    ricetta.setImg("Pizza.doc"); // Formato immagine non valido

    Set<ConstraintViolation<Ricetta>> violazioni = validator.validate(ricetta);

    assertEquals(1, violazioni.size());
    assertEquals(
        "Formato immagine non supportato. Carica un file in formato JPG o PNG",
        violazioni.iterator().next().getMessage());
  }

  /**
   * Testa l'aggiunta di una ricetta senza immagine.
   *
   * <p>Verifica che il sistema accetti una ricetta valida anche quando il campo immagine è null,
   * essendo facoltativo, e che salvi correttamente la ricetta.
   */
  @Test
  void testTC_GCCR_CRC_08() {
    Ricetta ricetta = creaRicetta();
    ricetta.setImg(null); // Nessuna immagine (facoltativa)

    when(ricettaRepository.save(ricetta)).thenReturn(ricetta);

    Ricetta risultato = gestioneRicetteService.aggiungiRicetta(ricetta);

    assertNotNull(risultato);
    assertEquals("Torta al Cioccolato", risultato.getNome());
    assertEquals("Ricetta pubblicata con successo!", "Ricetta pubblicata con successo!");
  }

  /**
   * Crea un'istanza predefinita di una ricetta valida.
   *
   * <p>Questa ricetta può essere utilizzata come base per i test, con valori già inizializzati che
   * rispettano le regole di validazione.
   *
   * @return un oggetto {@code Ricetta} preconfigurato
   */
  private Ricetta creaRicetta() {
    Ricetta ricetta = new Ricetta();
    ricetta.setNome("Torta al Cioccolato");
    ricetta.setIngredienti(List.of("Farina", " Uova", "Cioccolato"));
    ricetta.setIstruzioni("Mescolare e cuocere a 180 gradi");
    ricetta.setCategoria(CategoriaRicetta.DOLCE);
    ricetta.setQuantitaPerPersona(1);
    ricetta.setImg("Torta.jpg");
    return ricetta;
  }
}
