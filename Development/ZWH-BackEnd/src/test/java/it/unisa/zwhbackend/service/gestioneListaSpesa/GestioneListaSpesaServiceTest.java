package it.unisa.zwhbackend.service.gestioneListaSpesa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.enums.CategoriaAlimentare;
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
    utente.setEmail("test1@example.com");
    utente.setCategoria(
        Arrays.asList(
            CategoriaAlimentare.VEGANO.toString(), CategoriaAlimentare.VEGETARIANO.toString()));
  }

  @Test
  void test_TC_GPL_GL_01() {
    // PA1, PP1: Le liste coincidono
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            createPossiedeInFrigo(
                "Marinated Tofu 160 g",
                "5013683305466",
                Arrays.asList(CategoriaAlimentare.VEGANO.toString())),
            createPossiedeInFrigo(
                "Uova biologiche 6 uova",
                "8002790048554",
                Arrays.asList(CategoriaAlimentare.VEGANO.toString())));

    when(possiedeInFrigoRepository.findByUtenteEmail(utente.getEmail()))
        .thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    ListaSpesa result = gestioneListaSpesaService.generateShoppingList(utente);
    // System.out.println(result.toString());
    assertEquals(
        0, result.getProducts().size(), "La lista della spesa non dovrebbe contenere prodotti.");
  }

  @Test
  void test_TC_GPL_GL_02() {
    // PA2, PP1: Prodotti mancanti
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            createPossiedeInFrigo(
                "Nutella 400 g",
                "3017620422003",
                Arrays.asList(CategoriaAlimentare.SENZA_GLUTINE.toString())),
            createPossiedeInFrigo(
                "Uova biologiche 6 uova",
                "8002790048554",
                Arrays.asList(CategoriaAlimentare.VEGANO.toString())));

    // Spy sulla classe di servizio per simulare il piano giornaliero
    GestioneListaSpesaService gestioneListaSpesaServiceSpy = spy(gestioneListaSpesaService);

    // Mock dei repository
    when(possiedeInFrigoRepository.findByUtenteEmail(utente.getEmail()))
        .thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    // System.out.println("Prodotti in frigo: " + possiedeInFrigo);

    // Genera la lista della spesa
    ListaSpesa result = gestioneListaSpesaServiceSpy.generateShoppingList(utente);

    // Debug intermedio: verifica i risultati della lista generata
    System.out.println("Lista della spesa generata: " + result.getProducts());

    // Verifica che la lista contenga solo "Uova"
    assertNotNull(result, "La lista della spesa non dovrebbe essere null");
    assertEquals(
        1, result.getProducts().size(), "La lista della spesa dovrebbe contenere un solo prodotto");
    assertEquals(
        "Marinated Tofu 160 g",
        result.getProducts().get(0).getName(),
        "Il prodotto nella lista della spesa dovrebbe essere 'Uova'");
  }

  @Test
  void test_TC_GPL_GL_03() {
    // PA1, PP2: Liste coincidono, prodotto senza tipologia
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            createPossiedeInFrigo(
                "Marinated Tofu 160 g",
                "5013683305466",
                Arrays.asList(CategoriaAlimentare.SENZA_GLUTINE.toString())),
            createPossiedeInFrigo("Uova biologiche 6 uova", "8002790048554", Arrays.asList("")));

    when(possiedeInFrigoRepository.findByUtenteEmail(utente.getEmail()))
        .thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    // Genera la lista della spesa
    ListaSpesa result = gestioneListaSpesaService.generateShoppingList(utente);

    System.out.println(result.toString());

    // Verifica che la lista della spesa non sia null
    assertNotNull(result, "La lista della spesa non dovrebbe essere null");

    // Verifica che tutti i prodotti nella lista della spesa abbiano una categoria valida
    boolean allProductsHaveCategories =
        result.getProducts().stream()
            .allMatch(
                product -> product.getCategoria() != null && !product.getCategoria().isEmpty());

    assertTrue(
        allProductsHaveCategories,
        "Tutti i prodotti nella lista della spesa devono avere categorie valide.");
  }

  @Test
  void test_TC_GPL_GL_04() {
    // PA2, PP2: Prodotti mancanti, preferenze impostate
    List<PossiedeInFrigo> possiedeInFrigo =
        List.of(
            createPossiedeInFrigo(
                "Marinated Tofu 160 g",
                "3017620422003",
                Arrays.asList(CategoriaAlimentare.SENZA_GLUTINE.toString())),
            createPossiedeInFrigo(
                "Uova biologiche 6 uova",
                "8002790048554",
                Arrays.asList(CategoriaAlimentare.VEGANO.toString())));

    when(possiedeInFrigoRepository.findByUtenteEmail(utente.getEmail()))
        .thenReturn(possiedeInFrigo);
    when(possiedeInDispensaRepository.findByUtente(utente)).thenReturn(Collections.emptyList());

    ListaSpesa result = gestioneListaSpesaService.generateShoppingList(utente);

    System.out.println(result.toString());

    assertNotNull(result);
    // Verifica che la lista della spesa contenga esattamente un prodotto
    assertEquals(
        1, result.getProducts().size(), "La lista della spesa dovrebbe contenere un prodotto.");

    // Verifica che il prodotto nella lista abbia la categoria VEGANO
    boolean containsVegan =
        result.getProducts().stream()
            .anyMatch(
                product -> product.getCategoria().contains(CategoriaAlimentare.VEGANO.toString()));

    assertTrue(
        containsVegan, "La lista della spesa deve contenere un prodotto con categoria VEGANO.");
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
    List<String> preferences =
        List.of(
            CategoriaAlimentare.VEGANO.toString(), CategoriaAlimentare.SENZA_GLUTINE.toString());

    // Caso: Compatibile
    assertTrue(
        gestioneListaSpesaService.isCompatibleWithPreferences(
            List.of(CategoriaAlimentare.VEGANO.toString()), preferences));

    // Caso: Non compatibile
    assertFalse(
        gestioneListaSpesaService.isCompatibleWithPreferences(List.of("carne"), preferences));

    // Caso: Nessuna preferenza impostata
    assertTrue(
        gestioneListaSpesaService.isCompatibleWithPreferences(
            List.of(CategoriaAlimentare.VEGANO.toString()), Collections.emptyList()));

    // Caso: Prodotto senza categorie
    assertFalse(
        gestioneListaSpesaService.isCompatibleWithPreferences(
            Collections.emptyList(), preferences));
  }

  private PossiedeInFrigo createPossiedeInFrigo(
      String nomeProdotto, String codiceBarre, List<String> categoria) {
    Prodotto prodotto =
        new Prodotto(nomeProdotto, codiceBarre, categoria != null ? categoria : List.of());
    return new PossiedeInFrigo(utente, prodotto, 1, "25/12/2024");
  }
}
