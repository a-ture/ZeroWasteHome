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
  public Optional<SegnalazionePagamento> RisolviSegnalazione(
      Long id, GestorePagamento gestore, String dettagliRisoluzione) {
    Optional<SegnalazionePagamento> optionalSegnalazione = segnalazioneRepository.findById(id);

    // Se la segnalazione non esiste, ritorna Optional vuoto
    if (optionalSegnalazione.isEmpty()) {
      return Optional.empty();
    }

    SegnalazionePagamento segnalazione = optionalSegnalazione.get();

    // Se la segnalazione è in stato "IN_RISOLUZIONE", aggiorna lo stato a "RISOLTO"
    if (segnalazione.getStato().equals(StatoSegnalazione.IN_RISOLUZIONE)) {
      segnalazione.setStato(StatoSegnalazione.CHIUSA); // Aggiorna lo stato a RISOLTO
      segnalazione.setGestorePagamento(gestore); // Assegna il gestore che ha risolto il problema
      segnalazione.setDataRisoluzione(LocalDateTime.now()); // Imposta la data di risoluzione
      segnalazione.setDettagliRisoluzione(
          dettagliRisoluzione); // Imposta i dettagli della risoluzione
    } else {
      // Se la segnalazione non è in stato "IN_RISOLUZIONE", ritorna Optional vuoto
      return Optional.empty();
    }

    // Salva la segnalazione aggiornata nel repository
    segnalazioneRepository.save(segnalazione);

    return Optional.of(segnalazione); // Ritorna la segnalazione risolta
  }

  /**
   * Aggiorna lo stato di una segnalazione di pagamento.
   *
   * <p>Questo metodo consente di aggiornare lo stato di una segnalazione, passando attraverso le
   * transizioni da "APERTA" a "IN_RISOLUZIONE" e da "IN_RISOLUZIONE" a "RISOLTO". Se il processo
   * non può essere completato (ad esempio, la segnalazione non è in uno stato valido per il
   * cambio), il metodo restituisce un {@link Optional} vuoto.
   *
   * @param idSegnalazione l'identificativo della segnalazione da aggiornare
   * @param gestore il gestore che prende in carico o risolve la segnalazione
   * @param dettagliRisoluzione dettagli sulla risoluzione, se presenti
   * @return un {@link Optional} contenente la segnalazione aggiornata o vuoto se non modificabile
   */
  @Override
  public Optional<SegnalazionePagamento> aggiornaStatoSegnalazione(
      Long idSegnalazione, GestorePagamento gestore, String dettagliRisoluzione) {

    Optional<SegnalazionePagamento> optionalSegnalazione =
        segnalazioneRepository.findById(idSegnalazione);

    // Se la segnalazione non esiste, ritorna Optional vuoto
    if (optionalSegnalazione.isEmpty()) {
      return Optional.empty();
    }

    SegnalazionePagamento segnalazione = optionalSegnalazione.get();

    switch (segnalazione.getStato()) {
      case APERTA:
        // Se la segnalazione è APERTA e non ci sono dettagli, la si può passare a IN_RISOLUZIONE
        if (dettagliRisoluzione == null) {
          segnalazione.setStato(
              StatoSegnalazione.IN_RISOLUZIONE); // Transizione da APERTA a IN_RISOLUZIONE
          segnalazione.setGestorePagamento(gestore); // Assegna il gestore
        } else {
          return Optional.empty(); // Se ci sono dettagli, non si può passare a IN_RISOLUZIONE
        }
        break;
      case IN_RISOLUZIONE:
        // Se la segnalazione è in risoluzione e ci sono dettagli, la si può risolvere
        if (dettagliRisoluzione.isEmpty()) {
          return Optional.empty(); // Se mancano i dettagli, non è possibile risolvere
        }
        segnalazione.setStato(StatoSegnalazione.CHIUSA); // Transizione da IN_RISOLUZIONE a RISOLTO
        segnalazione.setDataRisoluzione(LocalDateTime.now()); // Imposta la data di risoluzione
        segnalazione.setDettagliRisoluzione(dettagliRisoluzione); // Imposta i dettagli
        break;

      case CHIUSA:
        // Se la segnalazione è già RISOLTO, non è possibile modificarla
        return Optional.empty();
    }

    // Salva le modifiche nel repository
    segnalazioneRepository.save(segnalazione);

    return Optional.of(segnalazione); // Ritorna la segnalazione aggiornata
  }
}
