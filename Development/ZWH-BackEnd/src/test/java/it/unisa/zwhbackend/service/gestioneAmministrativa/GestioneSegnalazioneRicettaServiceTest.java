package it.unisa.zwhbackend.service.gestioneAmministrativa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import it.unisa.zwhbackend.model.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * Test per il servizio GestioneSegnalazioneRicettaService, che gestisce le segnalazioni relative
 * alle ricette. I test verificano i vari scenari di risoluzione delle segnalazioni, comprese le
 * violazioni, i blocchi e le rimozioni.
 *
 * @author Giovanni Balzano
 */
@SpringBootTest
class GestioneSegnalazioneRicettaServiceTest {

  @MockBean private SegnalazioneRicettaRepository segnalazioneRicettaRepository;

  @MockBean private GestoreCommunityRepository gestoreRepository;

  @MockBean private RicettaRepository ricettaRepository;

  @MockBean private ListaBloccatiRepository listaBloccatiRepository;

  @MockBean private UtenteRepository utenteRepository;

  @Autowired private GestioneSegnalazioneRicettaService gestioneSegnalazioneRicettaService;

  private Utente autore;
  private Ricetta ricetta;
  private SegnalazioneRicetta segnalazione;
  private GestoreCommunity gestore;

  /**
   * Metodo di setup eseguito prima di ogni test. Crea gli oggetti necessari per eseguire i test.
   */
  @BeforeEach
  void setUp() {
    // Crea l'utente, la ricetta e la segnalazione per ogni test
    autore = new Utente();
    autore.setId(1L);
    autore.setEmail("test@domain.com");
    autore.setName("Test User");
    autore.setNumeroSegnalazioni(0);
    autore.setBloccato(false);

    ricetta = new Ricetta();
    ricetta.setId(1L);
    ricetta.setNome("Ricetta di test");
    ricetta.setAutore(autore);

    segnalazione = new SegnalazioneRicetta();
    segnalazione.setId(1L);
    segnalazione.setContenuto("Contenuto della segnalazione");
    segnalazione.setStato(StatoSegnalazione.IN_RISOLUZIONE);
    segnalazione.setRicettaAssociato(ricetta);

    gestore = new GestoreCommunity();
    gestore.setId(1L);
  }

  /**
   * Test per verificare che venga restituito un messaggio di errore quando il motivo del blocco è
   * vuoto.
   */
  // TC_GSR_RU_01 - Test per motivo del blocco vuoto
  @Test
  void testRisoluzioneMotivoBloccoNonValido_TC_GSR_RU_01() {
    String risultato = gestioneSegnalazioneRicettaService.risolviSegnalazione(1L, 1L, "");

    assertEquals("Il motivo del blocco è obbligatorio.", risultato);
  }

  /**
   * Test per verificare che venga restituito un messaggio di errore quando il motivo del blocco è
   * troppo lungo.
   */
  // TC_GSR_RU_02 - Test per motivo del blocco troppo lungo
  @Test
  void testRisoluzioneMotivoBloccoTroppoLungo_TC_GSR_RU_02() {
    String risultato =
        gestioneSegnalazioneRicettaService.risolviSegnalazione(1L, 1L, "A".repeat(501));

    assertEquals("Il motivo del blocco non può superare i 500 caratteri.", risultato);
  }

  /**
   * Test per verificare che la segnalazione venga risolta correttamente quando l'utente è alla sua
   * prima violazione. In questo caso, l'utente non viene bloccato, ma la ricetta viene eliminata.
   */
  // TC_GSR_RU_03 - Test per risoluzione segnalazione prima violazione
  @Test
  void testRisoluzioneSegnalazionePrimaViolazione_TC_GSR_RU_03() {
    autore.setNumeroSegnalazioni(1); // Primo blocco

    // Mocking repository
    when(segnalazioneRicettaRepository.findById(segnalazione.getId()))
        .thenReturn(java.util.Optional.of(segnalazione));
    when(gestoreRepository.findById(1L)).thenReturn(java.util.Optional.of(gestore));
    when(ricettaRepository.findById(ricetta.getId())).thenReturn(java.util.Optional.of(ricetta));
    when(utenteRepository.save(autore)).thenReturn(autore);

    // Test della risoluzione della segnalazione
    String risultato =
        gestioneSegnalazioneRicettaService.risolviSegnalazione(1L, 1L, "Motivo di blocco");

    // Verifica che il risultato sia quello atteso
    assertEquals(
        "L'utente è alla prima violazione e non viene bloccato, ma la ricetta è stata eliminata.",
        risultato);
    verify(utenteRepository).save(autore);
    verify(ricettaRepository).delete(ricetta);
    verify(segnalazioneRicettaRepository).save(segnalazione);
  }

  /**
   * Test per verificare che la segnalazione venga risolta correttamente quando l'utente è alla sua
   * seconda violazione. In questo caso, l'utente viene bloccato, la ricetta viene eliminata e
   * l'utente viene aggiunto alla lista bloccati.
   */
  @Test
  void testRisoluzioneSegnalazioneSecondaViolazione_TC_GSR_RU_04() {
    autore.setNumeroSegnalazioni(2); // Seconda violazione

    // Mocking repository
    when(segnalazioneRicettaRepository.findById(segnalazione.getId()))
        .thenReturn(java.util.Optional.of(segnalazione));
    when(gestoreRepository.findById(1L)).thenReturn(java.util.Optional.of(gestore));
    when(ricettaRepository.findById(ricetta.getId())).thenReturn(java.util.Optional.of(ricetta));

    // Simula il salvataggio dell'utente (verifica che venga eseguito una sola volta)
    when(utenteRepository.save(any(Utente.class))).thenReturn(autore);

    // Simula l'inserimento dell'utente nella lista bloccati
    when(listaBloccatiRepository.save(any(ListaBloccati.class))).thenReturn(new ListaBloccati());

    // Test della risoluzione della segnalazione
    String risultato =
        gestioneSegnalazioneRicettaService.risolviSegnalazione(1L, 1L, "Motivo di blocco");

    // Verifica che il risultato sia quello atteso
    assertEquals(
        "Segnalazione risolta con successo. Autore bloccato e ricetta eliminata. Motivazione: Motivo di blocco",
        risultato);

    // Verifica che il metodo `save()` sia stato invocato una sola volta per l'utente
    verify(utenteRepository, times(1)).save(any(Utente.class));

    // Cattura l'utente che viene passato al save() tramite ArgumentCaptor
    ArgumentCaptor<Utente> captor = ArgumentCaptor.forClass(Utente.class);
    verify(utenteRepository, times(1)).save(captor.capture()); // Verifica una sola invocazione

    // Verifica che l'utente venga aggiornato correttamente
    Utente utenteSalvato = captor.getValue();
    assertEquals(2, utenteSalvato.getNumeroSegnalazioni()); // Verifica il numero di segnalazioni
    assertTrue(utenteSalvato.getBloccato()); // Verifica che l'utente sia stato bloccato

    // Verifica che la ricetta venga eliminata
    verify(ricettaRepository).delete(ricetta);

    // Verifica che la segnalazione venga aggiornata
    verify(segnalazioneRicettaRepository).save(segnalazione);

    // Verifica che l'utente venga aggiunto alla lista bloccati
    verify(listaBloccatiRepository).save(any(ListaBloccati.class));

    // Verifica che la data di blocco venga correttamente impostata
    ArgumentCaptor<ListaBloccati> captorBloccato = ArgumentCaptor.forClass(ListaBloccati.class);
    verify(listaBloccatiRepository).save(captorBloccato.capture());
    ListaBloccati listaBloccato = captorBloccato.getValue();
    assertEquals(autore, listaBloccato.getUtente()); // Verifica che l'utente bloccato sia corretto
    assertNotNull(listaBloccato.getDataBlocco()); // Verifica che la data di blocco non sia nulla
    assertEquals(
        "Motivo di blocco", listaBloccato.getMotivoBlocco()); // Verifica che il motivo sia corretto
  }

  /**
   * Metodo di utilità per creare una segnalazione con ricetta.
   *
   * @return Una nuova segnalazione associata a una ricetta.
   */
  private SegnalazioneRicetta creaSegnalazioneConRicetta() {
    SegnalazioneRicetta segnalazione = new SegnalazioneRicetta();
    segnalazione.setContenuto("Contenuto della segnalazione");
    segnalazione.setRicettaAssociato(ricetta);
    segnalazione.setStato(StatoSegnalazione.APERTA);
    return segnalazione;
  }
}
