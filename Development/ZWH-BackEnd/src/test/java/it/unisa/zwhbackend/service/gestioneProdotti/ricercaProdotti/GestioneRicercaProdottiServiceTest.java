package it.unisa.zwhbackend.service.gestioneProdotti.ricercaProdotti;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.enums.CategoriaAlimentare;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe di test per il servizio {@link GestioneRicercaProdottiService}. Questa classe verifica il
 * comportamento della ricerca dei prodotti per nome in vari scenari, come risultati validi, assenza
 * di risultati o gestione di input non validi. Utilizza Mockito per simulare le dipendenze.
 *
 * @author Alessandra Trotta
 */
class GestioneRicercaProdottiServiceTest {

  private ProdottoRepository prodottoRepository;
  private GestioneRicercaProdottiService gestioneRicercaProdottiPerNomeService;

  /**
   * Configura l'ambiente di test prima di ogni esecuzione. Crea un mock per il repository dei
   * prodotti e inizializza il servizio da testare.
   */
  @BeforeEach
  void setUp() {
    prodottoRepository = mock(ProdottoRepository.class);
    gestioneRicercaProdottiPerNomeService = new GestioneRicercaProdottiService(prodottoRepository);
  }

  /**
   * Verifica che il servizio restituisca correttamente una lista di prodotti quando esistono
   * corrispondenze per il nome fornito.
   *
   * <p>Scenario testato: - Nome del prodotto presente nel database.
   *
   * @throws AssertionError se il risultato non corrisponde alle aspettative
   */
  @Test
  void testTC_GUS_RPN_01() {
    // Arrange: Preparazione del contesto di test
    String nomeProdotto = "Pasta";
    Prodotto prodotto = creaProdotto(nomeProdotto);
    when(prodottoRepository.findByNameContainingIgnoreCase(nomeProdotto))
        .thenReturn(List.of(prodotto));

    // Act: Esecuzione del metodo da testare
    List<Prodotto> risultato = gestioneRicercaProdottiPerNomeService.RicercaPerNome(nomeProdotto);

    // Assert: Verifica il risultato
    assertNotNull(risultato);
    assertEquals(1, risultato.size());
    assertEquals("Pasta", risultato.get(0).getName());
  }

  /**
   * Verifica che venga sollevata un'eccezione {@link NoSuchElementException} quando non vengono
   * trovati prodotti che corrispondono al nome fornito.
   *
   * <p>Scenario testato: - Nome del prodotto non presente nel database.
   *
   * @throws NoSuchElementException se nessun prodotto viene trovato
   */
  @Test
  void testTC_GUS_RPN_02() {
    // Arrange: Preparazione del contesto di test
    String nomeProdotto = "Spaghetti";
    when(prodottoRepository.findByNameContainingIgnoreCase(nomeProdotto))
        .thenReturn(Collections.emptyList());

    // Act & Assert: Verifica che venga sollevata l'eccezione corretta
    NoSuchElementException exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              gestioneRicercaProdottiPerNomeService.RicercaPerNome(nomeProdotto);
            });
    assertEquals("Nessun prodotto trovato", exception.getMessage());
  }

  /**
   * Metodo helper per creare un'istanza di {@link Prodotto} con un nome specifico.
   *
   * @param nome il nome del prodotto da creare
   * @return un'istanza di {@link Prodotto} con i dati specificati
   */
  private Prodotto creaProdotto(String nome) {
    Prodotto prodotto = new Prodotto();
    prodotto.setName(nome);
    prodotto.setCategoria(List.of(CategoriaAlimentare.VEGANO.toString()));
    return prodotto;
  }
}
