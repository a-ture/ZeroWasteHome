package it.unisa.zwhbackend.service.gestioneProdotto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.PossiedeInFrigo;
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

class GestioneProdottoServiceTest {

  private ProdottoRepository prodottoRepository;
  private PossiedeInFrigoRepository possiedeInFrigoRepository;
  private UtenteRepository utenteRepository;
  private GestioneProdottoService gestioneProdottoService;

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

  @Test
  void testCodiceBarreNonValido_TC_GCF_IPF_01() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte", "12/12/2024", "123456789", 2, 1L));
    assertEquals(
        "Il codice deve avere una lunghezza massima di 8 caratteri e deve contenere solo cifre.",
        exception.getMessage());
  }

  @Test
  void testNomeProdottoNonValido_TC_GCF_IPF_02() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte123!", "12/12/2024", "12345678", 2, 1L));
    assertEquals(
        "La lunghezza massima per questo campo è 50 caratteri e deve contenere solo lettere dell'alfabeto.",
        exception.getMessage());
  }

  @Test
  void testDataScadenzaNonValida_TC_GCF_IPF_03() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte", "2/31/2024", "12345678", 2, 1L));
    assertEquals("La Data deve essere del formato gg/mm/aa.", exception.getMessage());
  }

  @Test
  void testQuantitaNonValida_TC_GCF_IPF_04() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    "Latte", "31/2/2024", "12345678", -1, 1L));
    assertEquals(
        "La Quantità deve essere un numero positivo maggiore di zero.", exception.getMessage());
  }

  @Test
  void testAggiungiProdottoFrigoNuovoProdotto() {
    String nomeProdotto = "Latte";
    String dataScadenza = "12/12/2024";
    String codiceBarre = "12345678";
    int quantita = 5;
    Long idUtente = 1L;

    // Mock dei repository
    Utente utente = mock(Utente.class);
    Prodotto prodotto = new Prodotto(nomeProdotto, codiceBarre);
    when(utenteRepository.findById(idUtente)).thenReturn(Optional.of(utente));
    when(prodottoRepository.findByCodiceBarre(codiceBarre)).thenReturn(Optional.empty());

    // Verifica il flusso di creazione del prodotto
    Prodotto prodottoRestituito =
        gestioneProdottoService.aggiungiProdottoFrigo(
            nomeProdotto, dataScadenza, codiceBarre, quantita, idUtente);

    System.out.println("Prodotto restituito: " + prodottoRestituito);

    // Verifica che il prodotto non sia nullo
    assertNotNull(prodottoRestituito);
    assertEquals(nomeProdotto, prodottoRestituito.getName());
    assertEquals(codiceBarre, prodottoRestituito.getCodiceBarre());

    // Verifica che il prodotto sia stato salvato
    verify(prodottoRepository).save(any(Prodotto.class));
  }

  @Test
  void testAggiungiProdottoFrigoProdottoEsistente() {
    String nomeProdotto = "Latte";
    String dataScadenza = "12/12/2024";
    String codiceBarre = "12345678";
    int quantita = 5;
    Long idUtente = 1L;

    // Mock dei repository
    Utente utente = mock(Utente.class);
    Prodotto prodotto = new Prodotto(nomeProdotto, codiceBarre);
    PossiedeInFrigo relazioneEsistente = new PossiedeInFrigo(utente, prodotto, 3, dataScadenza);
    when(utenteRepository.findById(idUtente)).thenReturn(Optional.of(utente));
    when(prodottoRepository.findByCodiceBarre(codiceBarre)).thenReturn(Optional.of(prodotto));
    when(possiedeInFrigoRepository.findByUtenteAndProdotto(utente, prodotto))
        .thenReturn(List.of(relazioneEsistente));

    Prodotto prodottoRestituito =
        gestioneProdottoService.aggiungiProdottoFrigo(
            nomeProdotto, dataScadenza, codiceBarre, quantita, idUtente);

    assertNotNull(prodottoRestituito);
    assertEquals(
        8, relazioneEsistente.getQuantita()); // Verifica che la quantità sia stata aggiornata
    verify(possiedeInFrigoRepository)
        .save(any(PossiedeInFrigo.class)); // Verifica che la relazione sia stata aggiornata
  }

  @Test
  void testAggiungiProdottoFrigoUtenteNonTrovato() {
    String nomeProdotto = "Latte";
    String dataScadenza = "12/12/2024";
    String codiceBarre = "12345678";
    int quantita = 5;
    Long idUtente = 999L;

    when(utenteRepository.findById(idUtente)).thenReturn(Optional.empty());

    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () ->
                gestioneProdottoService.aggiungiProdottoFrigo(
                    nomeProdotto, dataScadenza, codiceBarre, quantita, idUtente));

    assertEquals("Utente con ID 999 non trovato", exception.getMessage());
  }
}
