package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import it.unisa.zwhbackend.model.repository.SegnalazionePagamentoRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servizio per la gestione delle segnalazioni di pagamento. Fornisce la logica per risolvere le
 * segnalazioni in sospeso e aggiornare il loro stato.
 *
 * <p>La classe implementa l'interfaccia {@link SegnalazionePagamentoService} e interagisce con il
 * repository {@link SegnalazionePagamentoRepository} per recuperare e salvare le segnalazioni di
 * pagamento.
 *
 * @author Benito Farina
 */
@Service
public class GestioneSegnalazionePagamentoService implements SegnalazionePagamentoService {

  private final SegnalazionePagamentoRepository segnalazioneRepository;

  /**
   * Costruttore per iniettare il {@link SegnalazionePagamentoRepository}.
   *
   * @param segnalazioneRepository il repository per l'entità {@link SegnalazionePagamento}
   */
  public GestioneSegnalazionePagamentoService(
      SegnalazionePagamentoRepository segnalazioneRepository) {
    this.segnalazioneRepository = segnalazioneRepository;
  }

  /**
   * Risolve una segnalazione di pagamento.
   *
   * <p>Questo metodo cambia lo stato della segnalazione in "RISOLTO" se la segnalazione è
   * attualmente in stato "IN_RISOLUZIONE". Inoltre, registra il gestore che ha risolto il problema
   * e la data di risoluzione. Se la segnalazione non è in stato "IN_RISOLUZIONE", il metodo
   * restituisce un {@link Optional} vuoto.
   *
   * @param id l'identificativo della segnalazione da risolvere
   * @param gestore il gestore che risolve la segnalazione
   * @return un {@link Optional} contenente la segnalazione aggiornata, se risolta, o vuoto se non
   *     risolvibile
   */
  @Override
  public Optional<SegnalazionePagamento> RisolviSegnalazione(Long id, GestorePagamento gestore) {
    Optional<SegnalazionePagamento> optionalSegnalazione = segnalazioneRepository.findById(id);

    // Se la segnalazione non esiste, ritorna Optional vuoto
    if (optionalSegnalazione.isEmpty()) {
      return Optional.empty();
    }

    SegnalazionePagamento segnalazione = optionalSegnalazione.get();

    // Se la segnalazione è in stato "IN_RISOLUZIONE", aggiorna lo stato a "RISOLTO"
    if (segnalazione.getStato().equals(StatoSegnalazione.IN_RISOLUZIONE)) {
      segnalazione.setStato(StatoSegnalazione.RISOLTO);
      segnalazione.setGestorePagamento(gestore); // Assegna il gestore che ha risolto il problema
      segnalazione.setDataRisoluzione(LocalDateTime.now()); // Imposta la data di risoluzione
    } else {
      // Se la segnalazione non è in stato "IN_RISOLUZIONE", ritorna Optional vuoto
      return Optional.empty();
    }

    // Salva la segnalazione aggiornata nel repository
    segnalazioneRepository.save(segnalazione);

    return Optional.of(segnalazione); // Ritorna la segnalazione risolta
  }
}
