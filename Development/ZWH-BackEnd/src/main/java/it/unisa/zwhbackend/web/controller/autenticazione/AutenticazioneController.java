package it.unisa.zwhbackend.web.controller.autenticazione;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.service.autenticazione.AutenticazioneService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST per la gestione dell'autenticazione degli utenti.
 *
 * <p>- Fornisce un endpoint per l'accesso al sistema tramite email e password. - Interagisce con il
 * servizio di autenticazione per verificare le credenziali degli utenti. - Restituisce un token JWT
 * in caso di autenticazione riuscita.
 *
 * <p>Endpoint principali: - POST /api/auth/login: Consente agli utenti di autenticarsi con email e
 * password.
 *
 * @author Marco Renella
 */
@RestController
@RequestMapping("/api/public")
public class AutenticazioneController {

  private final AutenticazioneService autenticazioneService;

  /**
   * Costruttore con iniezione delle dipendenze.
   *
   * @param autenticazioneService Servizio per gestire la logica di autenticazione
   */
  @Autowired
  public AutenticazioneController(AutenticazioneService autenticazioneService) {
    this.autenticazioneService = autenticazioneService;
  }

  /**
   * Endpoint per l'autenticazione di un utente tramite email e password. - Verifica le credenziali
   * e restituisce un token JWT in caso di successo.
   *
   * @param request Mappa contenente i parametri necessari per il login: - "email": L'email
   *     dell'utente fornita durante il login. - "password": La password dell'utente fornita durante
   *     il login.
   * @return `ResponseEntity` con il risultato dell'operazione
   */
  @Operation(summary = "Autentica un utente e genera un token JWT")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Autenticazione riuscita",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            example =
                                "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }"))),
        @ApiResponse(
            responseCode = "400",
            description = "Credenziali non valide",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            example =
                                "{ \"messaggio\": \"Errore durante l'autenticazione: Credenziali non valide\" }"))),
        @ApiResponse(
            responseCode = "500",
            description = "Errore interno del server",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore interno del server\" }")))
      })
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String password = request.get("password");
    try {
      // Chiama il servizio per autenticare l'utente e ottenere un token JWT
      String token = autenticazioneService.login(email, password);

      // Restituisce lo stato HTTP 200 con il token JWT nel corpo della risposta
      return ResponseEntity.ok().body(new TokenResponse(token));
    } catch (IllegalArgumentException e) {
      // Restituisce lo stato HTTP 400 con un messaggio di errore specifico
      return ResponseEntity.badRequest().body(new ErrorResponse("Credenziali non valide"));
    } catch (Exception e) {
      // Restituisce lo stato HTTP 500 per errori generici
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ErrorResponse("Errore interno del server"));
    }
  }

  /**
   * Endpoint per l'autenticazione di un gestore tramite email e password. - Verifica le credenziali
   * e restituisce un token JWT in caso di successo.
   *
   * @param request Mappa contenente i parametri necessari per il login: - "email": L'email
   *     dell'utente fornita durante il login. - "password": La password dell'utente fornita durante
   *     il login.
   * @return `ResponseEntity` con il risultato dell'operazione
   */
  @Operation(summary = "Autentica un gestore e genera un token JWT")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Autenticazione gestore riuscita",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            example =
                                "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }"))),
        @ApiResponse(
            responseCode = "400",
            description = "Credenziali non valide",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            example =
                                "{ \"messaggio\": \"Errore durante l'autenticazione: Credenziali non valide\" }"))),
        @ApiResponse(
            responseCode = "500",
            description = "Errore interno del server",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore interno del server\" }")))
      })
  @PostMapping("/admin/login")
  public ResponseEntity<?> loginAmministrativo(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String password = request.get("password");
    try {
      // Chiama il servizio per autenticare il gestore e ottenere un token JWT
      String token = autenticazioneService.loginAmministrativo(email, password);

      // Restituisce lo stato HTTP 200 con il token JWT nel corpo della risposta
      return ResponseEntity.ok().body(new TokenResponse(token));
    } catch (IllegalArgumentException e) {
      // Restituisce lo stato HTTP 400 con un messaggio di errore specifico
      return ResponseEntity.badRequest().body(new ErrorResponse("Credenziali non valide"));
    } catch (Exception e) {
      // Restituisce lo stato HTTP 500 per errori generici
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ErrorResponse("Errore interno del server"));
    }
  }

  // Classe per il formato del token nella risposta
  static class TokenResponse {
    private String token;

    public TokenResponse(String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }
  }

  // Classe per gestire messaggi di errore nella risposta
  static class ErrorResponse {
    private String messaggio;

    public ErrorResponse(String messaggio) {
      this.messaggio = messaggio;
    }

    public String getMessaggio() {
      return messaggio;
    }

    public void setMessaggio(String messaggio) {
      this.messaggio = messaggio;
    }
  }
}
