package it.unisa.zwhbackend.service.gestioneProdotti;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.PossiedeInDispensaRepository;
import it.unisa.zwhbackend.model.repository.PossiedeInFrigoRepository;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test della classe GestioneProdottoService.
 *
 * <p>La classe contiene test unitari per i metodi della classe GestioneProdottoService. Ogni test
 * verifica la corretta gestione dei prodotti nel sistema, in particolare l'aggiunta di un prodotto
 * nel frigo di un utente, con il controllo delle validazioni dei dati in ingresso.
 *
 * @author Marco Meglio
 */
class GestioneProdottoServiceTest {

  private ProdottoRepository prodottoRepository;
  private PossiedeInFrigoRepository possiedeInFrigoRepository;
  private UtenteRepository utenteRepository;
  private GestioneProdottoService gestioneProdottoService;

  /**
   * Configura l'ambiente di test prima di ogni test case. Crea i mock per i repository necessari e
   * l'istanza del servizio.
   */
  @BeforeEach
  void setUp() {
    prodottoRepository = mock(ProdottoRepository.class);
    possiedeInFrigoRepository = mock(PossiedeInFrigoRepository.class);
    utenteRepository = mock(UtenteRepository.class);
    gestioneProdottoService =
        new GestioneProdottoService(
            prodottoRepository,
            possiedeInFrigoRepository,
            mock(PossiedeInDispensaRepository.class),
            utenteRepository);
  }

  /**
   * Verifica che venga lanciata un'eccezione IllegalArgumentException se il codice a barre del
   * prodotto non è valido (lunghezza maggiore di 8 caratteri).
   */
  @Test
  void testCodiceBarreNonValido_TC_GCF_IPF_01() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte", "12/12/2024", "123456789", 2, "user@example.com"));
    assertEquals(
        "Il codice deve avere una lunghezza massima di 8 caratteri e deve contenere solo cifre.",
        exception.getMessage());
  }

  /**
   * Verifica che venga lanciata un'eccezione IllegalArgumentException se il nome del prodotto
   * contiene caratteri non validi (deve contenere solo lettere).
   */
  @Test
  void testNomeProdottoNonValido_TC_GCF_IPF_02() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte123!", "12/12/2024", "12345678", 2, "user@example.com"));
    assertEquals(
        "La lunghezza massima per questo campo è 50 caratteri e deve contenere solo lettere dell'alfabeto.",
        exception.getMessage());
  }

  /**
   * Verifica che venga lanciata un'eccezione IllegalArgumentException se la data di scadenza del
   * prodotto non è nel formato corretto (gg/mm/aaaa).
   */
  @Test
  void testDataScadenzaNonValida_TC_GCF_IPF_03() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte", "2/31/2024", "12345678", 2, "user@example.com"));
    assertEquals("La Data deve essere del formato gg/mm/aa.", exception.getMessage());
  }

  /**
   * Verifica che venga lanciata un'eccezione IllegalArgumentException se la quantità di prodotto
   * inserita è negativa o zero.
   */
  @Test
  void testQuantitaNonValida_TC_GCF_IPF_04() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte", "31/2/2024", "12345678", -1, "user@example.com"));
    assertEquals(
        "La Quantità deve essere un numero positivo maggiore di zero.", exception.getMessage());
  }

  /**
   * Testa l'aggiunta di un prodotto valido nel frigo di un utente. Verifica che il prodotto venga
   * correttamente inserito nel frigo.
   */
  @Test
  void testAggiungiProdottoFrigo_TC_GCF_IPF_05() {
    String nomeProdotto = "Latte";
    String dataScadenza = "27/12/2024";
    String codiceBarre = "12345678";
    int quantita = 1;
    String idUtente = "user@example.com";

    // Mock dei repository
    Utente utente = mock(Utente.class);
    Prodotto prodotto = new Prodotto(nomeProdotto, codiceBarre);
    when(utenteRepository.findByEmail(idUtente)).thenReturn(utente);
    when(prodottoRepository.findByCodiceBarre(codiceBarre)).thenReturn(Optional.empty());

    // Chiamata al metodo per aggiungere il prodotto nel frigo
    Prodotto prodottoRestituito =
        gestioneProdottoService.aggiungiProdottoFrigo(
            nomeProdotto, dataScadenza, codiceBarre, quantita, idUtente);

    // Verifica che il prodotto non sia nullo
    assertNotNull(prodottoRestituito);
    assertEquals(nomeProdotto, prodottoRestituito.getName());
    assertEquals(codiceBarre, prodottoRestituito.getCodiceBarre());

    // Verifica che il prodotto sia stato salvato
    verify(prodottoRepository).save(any(Prodotto.class));
  }
}
