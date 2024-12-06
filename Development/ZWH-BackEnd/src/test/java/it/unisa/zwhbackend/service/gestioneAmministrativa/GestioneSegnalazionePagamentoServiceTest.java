package it.unisa.zwhbackend.service.gestioneAmministrativa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import it.unisa.zwhbackend.model.repository.SegnalazionePagamentoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GestioneSegnalazionePagamentoServiceTest {

  @Mock
  private SegnalazionePagamentoRepository segnalazionePagamentoRepository; // Mock del repository

  @Mock
  private GestioneSegnalazionePagamentoService
      gestioneSegnalazionePagamentoServiceMock; // Per i test con mock

  private GestioneSegnalazionePagamentoService
      gestioneSegnalazionePagamentoServiceReale; // Per i test con istanza reale
  private Validator validator;

  @BeforeEach
  void setUp() {
    // Configura il mock
    MockitoAnnotations.openMocks(this);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    // Configura un'istanza reale del servizio per test specifici
    gestioneSegnalazionePagamentoServiceReale =
        new GestioneSegnalazionePagamentoService(segnalazionePagamentoRepository);
  }

  @Test
  void testTC_GPT_RPP_01() {
    // Usa il servizio reale
    GestorePagamento gestorePagamento = new GestorePagamento(1L, "Ciccio", "ciccio@gmail.com");

    SegnalazionePagamento segnalazione = new SegnalazionePagamento();
    segnalazione.setId(1L);
    segnalazione.setDettagliRisoluzione("");
    segnalazione.setStato(StatoSegnalazione.IN_RISOLUZIONE);
    segnalazione.setDataCreazione(LocalDateTime.now());

    when(segnalazionePagamentoRepository.findById(segnalazione.getId()))
        .thenReturn(Optional.of(segnalazione));

    Optional<SegnalazionePagamento> result =
        gestioneSegnalazionePagamentoServiceReale.aggiornaStatoSegnalazione(
            segnalazione.getId(), gestorePagamento, "");

    assertTrue(result.isEmpty(), "Expected Optional.empty() due to empty resolution details");
    verify(segnalazionePagamentoRepository).findById(segnalazione.getId());
    verify(segnalazionePagamentoRepository, never()).save(any(SegnalazionePagamento.class));
  }

  @Test
  void testTC_GPT_RPP_02() {
    // Crea un oggetto GestorePagamento fisso, anche se non utilizzato nel test
    GestorePagamento gestorePagamento = creaGestorePagamento();

    // Crea una segnalazione con dettagli risoluzione troppo lunga (> 500 caratteri)
    SegnalazionePagamento segnalazione = new SegnalazionePagamento();
    segnalazione.setDettagliRisoluzione("A".repeat(501)); // Dettagli > 500 caratteri
    segnalazione.setStato(StatoSegnalazione.IN_RISOLUZIONE);
    segnalazione.setDataCreazione(LocalDateTime.now());

    // Validazione dei vincoli
    Set<ConstraintViolation<SegnalazionePagamento>> violazioni = validator.validate(segnalazione);

    // Verifica che ci sia una violazione
    assertEquals(1, violazioni.size());

    // Verifica che il messaggio di errore sia quello corretto
    assertEquals(
        "Descrizione della segnalazione troppo lunga", violazioni.iterator().next().getMessage());

    // Assicurati che il metodo non venga chiamato
    // Usa il mock di gestioneSegnalazionePagamentoService
    verify(gestioneSegnalazionePagamentoServiceMock, never())
        .aggiornaStatoSegnalazione(anyLong(), any(GestorePagamento.class), anyString());
  }

  @Test
  void testTC_GPT_RPP_03() {
    // Usa il servizio reale
    GestorePagamento gestorePagamento = creaGestorePagamento();

    SegnalazionePagamento segnalazione = new SegnalazionePagamento();
    segnalazione.setId(1L);
    segnalazione.setStato(StatoSegnalazione.IN_RISOLUZIONE);
    segnalazione.setGestorePagamento(gestorePagamento);
    segnalazione.setDettagliRisoluzione("Problema risolto in modo dettagliato");
    segnalazione.setDataCreazione(LocalDateTime.now());

    when(segnalazionePagamentoRepository.findById(segnalazione.getId()))
        .thenReturn(Optional.of(segnalazione));

    when(segnalazionePagamentoRepository.save(any(SegnalazionePagamento.class)))
        .thenReturn(segnalazione);

    Optional<SegnalazionePagamento> result =
        gestioneSegnalazionePagamentoServiceReale.aggiornaStatoSegnalazione(
            segnalazione.getId(), gestorePagamento, "Problema risolto in modo dettagliato");

    assertTrue(result.isPresent(), "Expected a non-empty Optional with updated resolution details");
    assertEquals(
        StatoSegnalazione.CHIUSA, result.get().getStato(), "Expected status to be 'CHIUSA'");
    assertNotNull(result.get().getDataRisoluzione(), "Expected resolution date to be set");
    assertEquals("Problema risolto in modo dettagliato", result.get().getDettagliRisoluzione());

    verify(segnalazionePagamentoRepository).save(any(SegnalazionePagamento.class));
  }

  private GestorePagamento creaGestorePagamento() {
    return new GestorePagamento(1L, "Ciccio", "Ciccio@gmail.com");
  }
}
