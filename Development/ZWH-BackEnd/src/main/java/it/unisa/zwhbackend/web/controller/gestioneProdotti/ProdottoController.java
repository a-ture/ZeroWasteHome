package it.unisa.zwhbackend.web.controller.gestioneProdotti;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.ProdottoRequestDTO;
import it.unisa.zwhbackend.service.gestioneProdotti.ProdottoService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * {@code ProdottoController} è il controller REST che gestisce le operazioni relative ai prodotti.
 * In particolare, si occupa di aggiungere un prodotto al frigo di un utente, tramite il metodo
 * {@link #aggiungiProdottoFrigo}.
 *
 * @author Marco Meglio, Ferdinando Ranieri
 */
@RestController
@RequestMapping(
    "/api/utente/gestioneProdotto") // Mappa le richieste alla URL base "/api/gestioneProdotto"
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
  @PostMapping("/aggiungi-prodotto") // Mappa le richieste POST per aggiungere il prodotto
  public ResponseEntity<?> aggiungiProdottoFrigo(
      @RequestBody @Valid ProdottoRequestDTO prodottoRequestDTO, BindingResult bindingResult) {

    prodottoRequestDTO.setIdUtente(
        SecurityContextHolder.getContext().getAuthentication().getName());

    // Gestione della validazione
    if (bindingResult.hasErrors()) {
      List<Map<String, String>> errors =
          bindingResult.getFieldErrors().stream()
              .map(
                  fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("rejectedValue", String.valueOf(fieldError.getRejectedValue()));
                    error.put("message", fieldError.getDefaultMessage());
                    return error;
                  })
              .collect(Collectors.toList());
      return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY); // 422
    }

    try {
      // Logica principale
      Prodotto prodotto =
          prodottoService.aggiungiProdottoFrigo(
              prodottoRequestDTO.getNomeProdotto(),
              prodottoRequestDTO.getDataScadenza(),
              prodottoRequestDTO.getCodiceBarre(),
              prodottoRequestDTO.getQuantità(),
              prodottoRequestDTO.getIdUtente(),
              prodottoRequestDTO.getCategoria());
      return new ResponseEntity<>(prodotto, HttpStatus.OK); // 200 OK

    } catch (IllegalStateException e) {
      // Errore relativo allo stato del sistema (es. utente non trovato)
      return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST); // 400
    }
  }

  /**
   * Visualizza i prodotti nella dispensa di un utente.
   *
   * <p>Questo endpoint consente di ottenere tutti i prodotti presenti nella dispensa dell'utente
   * con l'ID specificato. La risposta conterrà la lista dei prodotti nella dispensa.
   *
   * @return una risposta HTTP con la lista dei prodotti nella dispensa
   */
  @Operation(
      summary = "Visualizza i prodotti nella dispensa") // Descrizione dell'operazione per Swagger
  @ApiResponses( // Definisce le risposte API per diversi casi
      value = {
        @ApiResponse(
            responseCode = "200", // Codice di risposta per successo
            description = "Prodotti visualizzati con successo nella dispensa",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Prodotto.class))),
        @ApiResponse(
            responseCode = "404", // Codice di risposta per utente non trovato
            description = "Utente non trovato",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Utente non trovato\" }"))),
        @ApiResponse(
            responseCode = "500", // Codice di risposta per errore interno
            description = "Errore interno del server",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore imprevisto\" }")))
      })
  @GetMapping("/dispensa")
  public ResponseEntity<?> visualizzaProdottiDispensa() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    try {
      List<ProdottoRequestDTO> prodottiDispensa = prodottoService.visualizzaProdottiDispensa(email);
      return new ResponseEntity<>(prodottiDispensa, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
