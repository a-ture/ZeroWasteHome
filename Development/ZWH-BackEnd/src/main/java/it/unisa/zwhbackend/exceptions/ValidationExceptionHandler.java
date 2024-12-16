package it.unisa.zwhbackend.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestore delle eccezioni di validazione. Questa classe intercetta le eccezioni di validazione
 * durante l'elaborazione dei dati nel controller. In caso di errore di validazione, restituisce una
 * risposta con il messaggio di errore.
 *
 * @author Marco Renella
 */
@RestControllerAdvice
public class ValidationExceptionHandler {

  /**
   * Gestisce le eccezioni di tipo MethodArgumentNotValidException. Questo metodo raccoglie gli
   * errori di validazione e li restituisce come un'unica stringa separata da virgola all'interno di
   * una mappa di risposta.
   *
   * @param ex l'eccezione MethodArgumentNotValidException che viene lanciata quando un parametro
   *     del metodo non è valido
   * @return ResponseEntity contenente la mappa con il messaggio di errore
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    // Raccolta degli errori di validazione
    String errori =
        ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage) // Prendiamo solo il messaggio
            .collect(
                Collectors.joining(
                    ", ")); // Uniamo tutti i messaggi in una stringa separata da virgola

    // Creazione della risposta con il campo 'messaggio' dentro l'oggetto 'error'
    Map<String, Object> risposta = new HashMap<>();
    risposta.put("messaggio", errori);

    // Restituisci la risposta con il messaggio di errore
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(risposta);
  }
}
