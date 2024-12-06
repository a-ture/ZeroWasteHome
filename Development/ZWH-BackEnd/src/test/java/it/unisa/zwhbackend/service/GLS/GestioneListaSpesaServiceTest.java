package it.unisa.zwhbackend.service.GLS;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.repository.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GestioneListaSpesaServiceTest {

  @Mock private ListaSpesaRepository listaSpesaRepository;

  @Mock private PossiedeInFrigoRepository possiedeInFrigoRepository;

  @Mock private PossiedeInDispensaRepository possiedeInDispensaRepository;

  @InjectMocks private GestioneListaSpesaService gestioneListaSpesaService;

  private Utente utente;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Setup utente
    utente = new Utente();
    utente.setId(1L);
    utente.setCategoria(Arrays.asList("vegano", "senza-glutine"));
  }

  @Test
  void test_TC_GPL_GL_01() {
    // PA1, PP1: Le liste coincidono
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            createPossiedeInFrigo("Latte", "senza-glutine"),
            createPossiedeInFrigo("Pane", "vegano"));

    List<Prodotto> pianoGiornaliero =
        List.of(createProdotto("Latte", "senza-glutine"), createProdotto("Pane", "vegano"));

    when(possiedeInFrigoRepository.findByUtenteId(utente.getId())).thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    ListaSpesa result = gestioneListaSpesaService.generateShoppingList(utente);

    // Verifica che la lista della spesa sia vuota
    assertNotNull(result);
    assertTrue(result.getProducts().isEmpty());
  }

  @Test
  void test_TC_GPL_GL_02() {
    // PA2, PP1: Prodotti mancanti
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            createPossiedeInFrigo("Latte", "senza-glutine"),
            createPossiedeInFrigo("Pane", "vegano"));

    // Spy sulla classe di servizio per simulare il piano giornaliero
    GestioneListaSpesaService gestioneListaSpesaServiceSpy = spy(gestioneListaSpesaService);

    // Simula il piano giornaliero statico
    List<Prodotto> pianoGiornaliero =
        List.of(
            new Prodotto("Latte", "1", List.of("senza-glutine")),
            new Prodotto("Pane", "2", List.of("vegano")),
            new Prodotto("Uova", "3", List.of("vegano")));
    when(gestioneListaSpesaServiceSpy.createStaticDailyPlanItems()).thenReturn(pianoGiornaliero);

    // Mock dei repository
    when(possiedeInFrigoRepository.findByUtenteId(utente.getId())).thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    System.out.println("Prodotti in frigo: " + possiedeInFrigo);
    System.out.println("Piano giornaliero: " + pianoGiornaliero);

    // Genera la lista della spesa
    ListaSpesa result = gestioneListaSpesaServiceSpy.generateShoppingList(utente);

    // Debug intermedio: verifica i risultati della lista generata
    System.out.println("Lista della spesa generata: " + result.getProducts());

    // Verifica che la lista contenga solo "Uova"
    assertNotNull(result, "La lista della spesa non dovrebbe essere null");
    assertEquals(
        1, result.getProducts().size(), "La lista della spesa dovrebbe contenere un solo prodotto");
    assertEquals(
        "Uova",
        result.getProducts().get(0).getName(),
        "Il prodotto nella lista della spesa dovrebbe essere 'Uova'");
  }

  @Test
  void test_TC_GPL_GL_03() {
    // PA1, PP2: Liste coincidono, prodotto senza tipologia
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            createPossiedeInFrigo("Latte", ""), // Prodotto senza categorie
            createPossiedeInFrigo("Pane", "") // Prodotto senza categorie
            );

    List<Prodotto> pianoGiornaliero =
        List.of(
            createProdotto("Latte", ""), // Prodotto senza categorie
            createProdotto("Pane", "") // Prodotto senza categorie
            );

    when(possiedeInFrigoRepository.findByUtenteId(utente.getId())).thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    // Genera la lista della spesa
    ListaSpesa result = gestioneListaSpesaService.generateShoppingList(utente);

    // Verifica che la lista della spesa sia vuota
    assertNotNull(result, "La lista della spesa non dovrebbe essere null");
    assertTrue(result.getProducts().isEmpty(), "La lista della spesa dovrebbe essere vuota");
  }

  @Test
  void test_TC_GPL_GL_04() {
    // PA2, PP2: Prodotti mancanti, preferenze impostate
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(createPossiedeInFrigo("Latte", "senza-glutine"), createPossiedeInFrigo("Pane", ""));

    List<Prodotto> pianoGiornaliero =
        List.of(
            createProdotto("Latte", "senza-glutine"),
            createProdotto("Pane", ""),
            createProdotto("Uova", ""));

    when(possiedeInFrigoRepository.findByUtenteId(utente.getId())).thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    ListaSpesa result = gestioneListaSpesaService.generateShoppingList(utente);

    // Verifica che la lista contenga solo "Uova" (non compatibile con preferenze)
    assertNotNull(result);
    assertEquals(0, result.getProducts().size());
  }

  @Test
  void testIsExpiring() {
    LocalDate today = LocalDate.now();
    // Caso: Data di scadenza oggi
    assertTrue(
        gestioneListaSpesaService.isExpiring(
            today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), today));
    // Caso: Data di scadenza nei prossimi 2 giorni
    assertTrue(
        gestioneListaSpesaService.isExpiring(
            today.plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), today));
    // Caso: Data di scadenza passata
    assertFalse(
        gestioneListaSpesaService.isExpiring(
            today.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), today));
    // Caso: Data di scadenza troppo futura
    assertFalse(
        gestioneListaSpesaService.isExpiring(
            today.plusDays(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), today));
    // Caso: Data di scadenza null
    assertFalse(gestioneListaSpesaService.isExpiring(null, today));
  }

  @Test
  void testIsCompatibleWithPreferences() {
    List<String> preferences = List.of("vegano", "senza-glutine");

    // Caso: Compatibile
    assertTrue(
        gestioneListaSpesaService.isCompatibleWithPreferences(List.of("vegano"), preferences));

    // Caso: Non compatibile
    assertFalse(
        gestioneListaSpesaService.isCompatibleWithPreferences(List.of("carne"), preferences));

    // Caso: Nessuna preferenza impostata
    assertTrue(
        gestioneListaSpesaService.isCompatibleWithPreferences(
            List.of("vegano"), Collections.emptyList()));

    // Caso: Prodotto senza categorie
    assertFalse(
        gestioneListaSpesaService.isCompatibleWithPreferences(
            Collections.emptyList(), preferences));
  }

  @Test
  void testGenerateShoppingList_ExpiringProducts() {
    LocalDate today = LocalDate.now();

    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            new PossiedeInFrigo(
                utente,
                createProdotto("Latte", "senza-glutine"),
                1,
                today.plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))), // In scadenza
            new PossiedeInFrigo(
                utente,
                createProdotto("Pane", "vegano"),
                1,
                today
                    .plusDays(10)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) // Non in scadenza
            );

    when(possiedeInFrigoRepository.findByUtenteId(utente.getId())).thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    ListaSpesa result = gestioneListaSpesaService.generateShoppingList(utente);

    assertNotNull(result, "La lista della spesa non dovrebbe essere null");
    assertEquals(
        1,
        result.getProducts().size(),
        "La lista della spesa dovrebbe contenere solo i prodotti in scadenza");
    assertEquals(
        "Latte",
        result.getProducts().get(0).getName(),
        "Il prodotto nella lista della spesa dovrebbe essere 'Latte'");
  }

  private PossiedeInFrigo createPossiedeInFrigo(String nomeProdotto, String categoria) {
    Prodotto prodotto =
        new Prodotto(nomeProdotto, "123", categoria != null ? List.of(categoria) : List.of());
    return new PossiedeInFrigo(utente, prodotto, 1, "25/12/2024");
  }

  private Prodotto createProdotto(String name, String categoria) {
    Prodotto prodotto = new Prodotto();
    prodotto.setName(name);
    prodotto.setCategoria(categoria == null ? null : List.of(categoria));
    return prodotto;
  }
}
