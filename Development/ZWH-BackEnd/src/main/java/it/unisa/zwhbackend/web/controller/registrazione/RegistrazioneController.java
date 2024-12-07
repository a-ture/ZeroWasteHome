package it.unisa.zwhbackend.web.controller.registrazione;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.service.registazione.RegistrazioneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utenti")
public class RegistrazioneController {

  private final RegistrazioneService registrazioneService;

  @Autowired
  public RegistrazioneController(RegistrazioneService registrazioneService) {
    this.registrazioneService = registrazioneService;
  }

  /**
   * Metodo POST per registrare un nuovo utente.
   *
   * <p>Questo metodo consente di registrare un nuovo utente nel sistema. Restituisce un messaggio
   * di errore se l'email è già esistente o se i dati dell'utente non sono validi.
   *
   * @param utente l'utente da registrare
   * @return l'utente registrato oppure un errore
   */
  @Operation(summary = "Registra un nuovo utente")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Utente registrato con successo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Utente.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Errore di validazione o utente già esistente",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Email già registrata\" }"))),
        @ApiResponse(
            responseCode = "500",
            description = "Errore interno del server",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore imprevisto\" }")))
      })
  @PostMapping("/registrazioneUtente")
  public ResponseEntity<Utente> registrazione(@RequestBody @Valid Utente utente) {
    Utente nuovoUtente = registrazioneService.registrazione(utente);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
