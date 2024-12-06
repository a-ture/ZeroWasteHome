package it.unisa.zwhbackend.service.gestioneProdotto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.PossiedeInDispensa;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.PossiedeInDispensaRepository;
import it.unisa.zwhbackend.model.repository.PossiedeInFrigoRepository;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test per il servizio di gestione dei prodotti nella dispensa.
 *
 * <p>Questo test verifica che il servizio `ProdottoService` gestisca correttamente la
 * visualizzazione dei prodotti presenti nella dispensa di un utente.
 */
@SpringBootTest
class GestioneProdottoDispensaServiceTest {

  private PossiedeInDispensaRepository possiedeInDispensaRepository;
  private GestioneProdottoService gestioneProdottoService;
  private ProdottoRepository prodottoRepository;
  private PossiedeInFrigoRepository possiedeInFrigoRepository;
  private UtenteRepository utenteRepository;

  @BeforeEach
  void setUp() {
    possiedeInDispensaRepository = mock(PossiedeInDispensaRepository.class);

    gestioneProdottoService =
        new GestioneProdottoService(
            prodottoRepository,
            possiedeInFrigoRepository,
            possiedeInDispensaRepository,
            utenteRepository);
  }

  /**
   * Test del caso in cui un utente con ID valido ha due prodotti nella dispensa. Verifica che il
   * metodo `visualizzaProdottiDispensa` restituisca correttamente la lista dei prodotti.
   */
  @Test
  void testTC_GCD_VPD_01() {
    // Crea un utente con ID 1
    Utente utente = new Utente();
    utente.setId(1L);

    // Crea due prodotti
    Prodotto prodotto1 = new Prodotto("Prodotto 1", "123456");
    Prodotto prodotto2 = new Prodotto("Prodotto 2", "789101");

    // Crea due relazioni per l'utente con i prodotti
    PossiedeInDispensa relazione1 = new PossiedeInDispensa(utente, prodotto1, 1, "2024-12-30");
    PossiedeInDispensa relazione2 = new PossiedeInDispensa(utente, prodotto2, 2, "2024-12-31");

    // Configura i mock per restituire l'utente e le due relazioni
    when(utenteRepository.findById(1L)).thenReturn(Optional.of(utente));
    when(possiedeInDispensaRepository.findByUtente(utente))
        .thenReturn(List.of(relazione1, relazione2));

    // Invoca il metodo per ottenere i prodotti in dispensa

    List<Prodotto> prodotti = gestioneProdottoService.visualizzaProdottiDispensa(1L);

    // Verifica che la lista non sia nulla e contenga i due prodotti
    assertNotNull(prodotti);
    assertEquals(2, prodotti.size());
    assertTrue(prodotti.contains(prodotto1));
    assertTrue(prodotti.contains(prodotto2));
  }

  /**
   * Test del caso in cui un utente non abbia prodotti nella dispensa. Verifica che il metodo
   * `visualizzaProdottiDispensa` restituisca una lista vuota quando non ci sono prodotti associati
   * all'utente.
   */
  @Test
  void testTC_GCD_VPD_02() {
    // Simula un utente con ID 2
    Utente utente = new Utente();
    utente.setId(2L);

    // Configura i mock per restituire un utente e una lista vuota di relazioni
    when(utenteRepository.findById(2L)).thenReturn(Optional.of(utente));
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(List.of());

    // Invoca il metodo e verifica che restituisca una lista vuota
    List<Prodotto> result = gestioneProdottoService.visualizzaProdottiDispensa(2L);

    // Verifica che la lista restituita sia vuota
    assertTrue(result.isEmpty(), "Nessun prodotto in Dispensa.");
  }
}
