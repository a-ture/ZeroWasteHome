package it.unisa.zwhbackend.service.gestioneProdotti;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.PossiedeInDispensa;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.ProdottoRequestDTO;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.PossiedeInDispensaRepository;
import it.unisa.zwhbackend.model.repository.PossiedeInFrigoRepository;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test per il servizio di gestione dei prodotti nella dispensa.
 *
 * <p>Questo test verifica che il servizio `ProdottoService` gestisca correttamente la
 * visualizzazione dei prodotti presenti nella dispensa di un utente.
 *
 * @author Ferdinando
 */
@SpringBootTest
class GestioneProdottoDispensaServiceTest {

  private PossiedeInDispensaRepository possiedeInDispensaRepository;
  private GestioneProdottoService gestioneProdottoService;
  private ProdottoRepository prodottoRepository;
  private PossiedeInFrigoRepository possiedeInFrigoRepository;
  private UtenteRepository utenteRepository;

  /**
   * Configura i mock necessari per il test e inizializza il servizio da testare.
   *
   * <p>I mock simulano il comportamento dei repository utilizzati dal servizio.
   */
  @BeforeEach
  void setUp() {
    possiedeInDispensaRepository = mock(PossiedeInDispensaRepository.class);
    utenteRepository = mock(UtenteRepository.class);
    prodottoRepository = mock(ProdottoRepository.class);
    possiedeInFrigoRepository = mock(PossiedeInFrigoRepository.class);

    gestioneProdottoService =
        new GestioneProdottoService(
            prodottoRepository,
            possiedeInFrigoRepository,
            possiedeInDispensaRepository,
            utenteRepository);
  }

  /**
   * Test del caso in cui un utente con ID valido ha due prodotti nella dispensa.
   *
   * <p>Verifica che il metodo `visualizzaProdottiDispensa` restituisca correttamente la lista dei
   * prodotti.
   *
   * <p>Scenario: - L'utente "user@example.com" ha due prodotti associati nella dispensa.
   *
   * <p>Mock configurati: - Il repository degli utenti restituisce l'utente. - Il repository delle
   * relazioni restituisce i prodotti associati alla dispensa.
   */
  @Test
  void testTC_GCD_VPD_01() {
    // Crea un utente con email
    Utente utente = new Utente();
    utente.setEmail("user@example.com");

    // Crea due prodotti
    Prodotto prodotto1 = new Prodotto("Prodotto1", "123456", List.of("GLUTENFREE"));
    Prodotto prodotto2 = new Prodotto("Prodotto2", "789101", List.of("GLUTENFREE"));

    // Crea due relazioni per l'utente con i prodotti
    PossiedeInDispensa relazione1 = new PossiedeInDispensa(utente, prodotto1, 1, "30/12/2024");
    PossiedeInDispensa relazione2 = new PossiedeInDispensa(utente, prodotto2, 2, "31/12/2024");

    // Configura i mock per restituire l'utente e le due relazioni
    when(utenteRepository.findByEmail("user@example.com")).thenReturn(utente);
    when(possiedeInDispensaRepository.findByUtente(utente))
        .thenReturn(List.of(relazione1, relazione2));

    // Invoca il metodo per ottenere i prodotti in dispensa
    List<ProdottoRequestDTO> prodotti =
        gestioneProdottoService.visualizzaProdottiDispensa("user@example.com");

    // Verifica che la lista non sia nulla e contenga i due prodotti
    assertNotNull(prodotti, "La lista dei prodotti non dovrebbe essere null.");
    assertEquals(2, prodotti.size(), "La lista dovrebbe contenere 2 prodotti.");

    // Verifica che i dati siano corretti
    ProdottoRequestDTO prodottoDTO1 = prodotti.get(0);
    ProdottoRequestDTO prodottoDTO2 = prodotti.get(1);

    assertEquals("Prodotto1", prodottoDTO1.getNomeProdotto());
    assertEquals("123456", prodottoDTO1.getCodiceBarre());
    assertEquals("30/12/2024", prodottoDTO1.getDataScadenza());
    assertEquals(1, prodottoDTO1.getQuantità());
    assertEquals("user@example.com", prodottoDTO1.getIdUtente());

    assertEquals("Prodotto2", prodottoDTO2.getNomeProdotto());
    assertEquals("789101", prodottoDTO2.getCodiceBarre());
    assertEquals("31/12/2024", prodottoDTO2.getDataScadenza());
    assertEquals(2, prodottoDTO2.getQuantità());
    assertEquals("user@example.com", prodottoDTO2.getIdUtente());
  }

  /**
   * Test del caso in cui un utente non abbia prodotti nella dispensa.
   *
   * <p>Verifica che il metodo `visualizzaProdottiDispensa` restituisca una lista vuota quando non
   * ci sono prodotti associati all'utente.
   *
   * <p>Scenario: - L'utente "user@example.com" non ha prodotti associati nella dispensa.
   *
   * <p>Mock configurati: - Il repository degli utenti restituisce l'utente. - Il repository delle
   * relazioni restituisce una lista vuota.
   */
  @Test
  void testTC_GCD_VPD_02() {
    // Simula un utente con email
    Utente utente = new Utente();
    utente.setEmail("user@example.com");

    // Configura i mock per restituire un utente e una lista vuota di relazioni
    when(utenteRepository.findByEmail("user@example.com")).thenReturn(utente);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(List.of());

    // Invoca il metodo e verifica che restituisca una lista vuota
    List<ProdottoRequestDTO> result =
        gestioneProdottoService.visualizzaProdottiDispensa("user@example.com");

    // Verifica che la lista restituita sia vuota
    assertNotNull(result, "La lista non dovrebbe essere null.");
    assertTrue(result.isEmpty(), "La lista dovrebbe essere vuota.");
  }
}
