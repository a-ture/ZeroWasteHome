package it.unisa.zwhbackend.web.controller.gestioneProdotti.estrazioneDatiAPI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.service.gestioneProdotti.estrazioneDatiAPI.DataExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per la gestione dell'estrazione dei dati dei prodotti tramite API.
 * Fornisce endpoint per ottenere i dettagli di un prodotto utilizzando il codice a barre.
 *
 * <p>Autore: Marco Renella</p>
 */
@RestController
@RequestMapping("/api/productExctraction")
public class DataExtractionController {

    @Autowired
    private DataExtractionService extractionService;

    /**
     * Recupera i dettagli di un prodotto utilizzando il codice a barre fornito.
     *
     * @param barcode il codice a barre del prodotto da cercare.
     * @return un oggetto ResponseEntity contenente i dettagli del prodotto se trovato
     *         oppure un messaggio di errore in caso di problemi.
     */
    @Operation(summary = "Recupera i dettagli di un prodotto tramite codice a barre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dettagli del prodotto trovati con successo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"productName\": \"Latte (1L)\", \"nutrition\": \"Valori nutrizionali per 100 grammi:\\nEnergia: 200 kj (48 kcal)\\nGrassi: 1.5 g\\n...\", \"notes\": \"Prodotto biologico.\\nIngredienti: Latte intero.\" }"))),
            @ApiResponse(responseCode = "400", description = "Errore nella richiesta, ad esempio codice a barre non valido o prodotto non trovato",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Prodotto non trovato. Controlla il codice a barre."))),
            @ApiResponse(responseCode = "500", description = "Errore interno del server durante la comunicazione con l'API Open Food Facts",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Errore interno durante il recupero dei dettagli del prodotto.")))
    })
    @GetMapping("/{barcode}")
    public ResponseEntity<?> getProductDetails(@PathVariable String barcode) {
        try {
            return ResponseEntity.ok(extractionService.getProductDetails(barcode).toString());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
