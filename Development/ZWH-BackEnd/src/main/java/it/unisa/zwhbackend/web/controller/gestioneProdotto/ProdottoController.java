package it.unisa.zwhbackend.web.controller.gestioneProdotto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.ProdottoRequestDTO;
import it.unisa.zwhbackend.service.gestioneProdotto.ProdottoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * {@code ProdottoController} è il controller REST che gestisce le operazioni relative ai prodotti.
 * In particolare, si occupa di aggiungere un prodotto al frigo di un utente, tramite il metodo
 * {@link #aggiungiProdottoFrigo}.
 *
 * @author Marco Meglio
 */
@RestController
@RequestMapping("/api/gestioneProdotto") // Mappa le richieste alla URL base "/api/gestioneProdotto"
public class ProdottoController {

  private final ProdottoService prodottoService; // Servizio per gestire i prodotti

  /**
   * Costruttore del controller che inietta il servizio {@link ProdottoService}.
   *
   * @param prodottoService il servizio per gestire i prodotti
   */
  @Autowired
  public ProdottoController(ProdottoService prodottoService) {
    this.prodottoService = prodottoService;
  }

  /**
   * Aggiunge un prodotto al frigo di un utente.
   *
   * <p>Questo endpoint consente di aggiungere un prodotto al frigo di un utente. Se il prodotto non
   * esiste, viene creato; se esiste già, la quantità viene aggiornata.
   *
   * @param prodottoRequestDTO il DTO contenente i dati del prodotto
   * @param bindingResult il risultato della validazione dei dati inviati
   * @return una risposta HTTP con il prodotto aggiunto o un errore
   */
  @Operation(summary = "Aggiungi un prodotto a un frigo") // Descrizione dell'operazione per Swagger
  @ApiResponses( // Definisce le risposte API per diversi casi
      value = {
        @ApiResponse(
            responseCode = "200", // Codice di risposta per successo
            description = "Prodotto aggiunto in frigo con successo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Prodotto.class))),
        @ApiResponse(
            responseCode = "400", // Codice di risposta per errore di validazione
            description = "Errore di validazione",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore di validazione\" }"))),
        @ApiResponse(
            responseCode = "404", // Codice di risposta per prodotto non trovato
            description = "Prodotto non trovato",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Prodotto non trovato\" }"))),
        @ApiResponse(
            responseCode = "500", // Codice di risposta per errore interno
            description = "Errore interno del server",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore imprevisto\" }")))
      })
  @PostMapping("/aggiungi-prodotto/") // Mappa le richieste POST per aggiungere il prodotto
  public ResponseEntity<?> aggiungiProdottoFrigo(
      @RequestBody @Valid ProdottoRequestDTO prodottoRequestDTO, BindingResult bindingResult) {

    // Verifica la validità dei dati ricevuti nel corpo della richiesta
    if (bindingResult.hasErrors()) {
      return new ResponseEntity<>(
          bindingResult.getAllErrors(),
          HttpStatus.BAD_REQUEST); // Restituisce un errore di validazione
    }
    System.out.println("----------------------------\n Prodotto in inserimento \n -Nome: " + prodottoRequestDTO.getNomeProdotto() + "\n -Id Utente: " + prodottoRequestDTO.getIdUtente() + "\n--------------------");
    try {
      // Aggiungi il prodotto al frigo utilizzando i dati del DTO
      Prodotto prodotto =
          prodottoService.aggiungiProdottoFrigo(
              prodottoRequestDTO.getNomeProdotto(),
              prodottoRequestDTO.getDataScadenza(),
              prodottoRequestDTO.getCodiceBarre(),
              prodottoRequestDTO.getQuantità()
              ,prodottoRequestDTO.getIdUtente());
      return new ResponseEntity<>(
          prodotto, HttpStatus.OK); // Restituisce il prodotto aggiunto con successo
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>( // Restituisce un errore 404 se il prodotto non viene trovato
          null, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>( // Restituisce un errore 500 in caso di errore imprevisto
          null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
