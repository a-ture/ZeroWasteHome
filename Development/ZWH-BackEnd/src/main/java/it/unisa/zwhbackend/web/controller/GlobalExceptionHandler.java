package it.unisa.zwhbackend.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe per la gestione globale delle eccezioni in un'applicazione Spring Boot. Intercetta e
 * gestisce le eccezioni comuni, fornendo risposte strutturate ai client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Gestisce eccezioni di validazione.
   *
   * @param ex eccezione di validazione
   * @return mappa con i messaggi di errore per ciascun campo
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  /**
   * Gestisce le eccezioni personalizzate di registrazione.
   *
   * @param ex eccezione di registrazione
   * @return messaggio di errore
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("messaggio", ex.getMessage()));
  }

  /**
   * Gestisce errori generici.
   *
   * @param ex eccezione generica
   * @return messaggio di errore generico
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("messaggio", "Errore imprevisto: " + ex.getMessage()));
  }
}
