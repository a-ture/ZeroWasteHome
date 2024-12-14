package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.annotations.ExcludeGeneratedFromCodeCoverage;
import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GestioneAmministrazioneService implements AmministrazioneService {

  private final SegnalazionePagamentoService pagamentoService;
  private final SegnalazioneRicettaService ricettaService;

  public GestioneAmministrazioneService(
      SegnalazionePagamentoService pagamentoService, SegnalazioneRicettaService ricettaService) {
    this.pagamentoService = pagamentoService;
    this.ricettaService = ricettaService;
  }

  @Override
  @ExcludeGeneratedFromCodeCoverage
  public Optional<SegnalazionePagamento> risolviSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione) {
    return pagamentoService.aggiornaStatoSegnalazione(id, gestorePagamento, dettagliRisoluzione);
  }

  @Override
  public Optional<SegnalazionePagamento> aggiornaStatoSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione) {
    return pagamentoService.aggiornaStatoSegnalazione(id, gestorePagamento, dettagliRisoluzione);
  }

  @Override
  public String risolviSegnalazioneRicetta(Long id, String gestoreId, String motivoBlocco) {
    return ricettaService.risolviSegnalazione(id, gestoreId, motivoBlocco);
  }
}
