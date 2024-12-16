import { Injectable } from '@angular/core'; // Importa il decoratore Injectable per definire un servizio
import { HttpClient, HttpHeaders } from '@angular/common/http'; // Importa HttpClient per effettuare chiamate HTTP
import { Observable } from 'rxjs'; // Importa Observable per gestire operazioni asincrone
import { environment } from '../../../environments/environment'; // Importa l'ambiente per configurare l'URL base dell'API

// Interfaccia per definire la struttura di un oggetto SegnalazionePagamento
export interface SegnalazionePagamento {
  id: number; // ID della segnalazione
  descrizione: string; // Descrizione del problema
  utente: string; // Nome dell'utente che ha creato la segnalazione
  dataCreazione: string; // Data di creazione della segnalazione
  statoSegnalazione: string; // Stato della segnalazione (es. APERTA, IN_CORSO, CHIUSA)
  dettagli: string; // Dettagli aggiuntivi della segnalazione
}

@Injectable({
  providedIn: 'root', // Configura il servizio come disponibile globalmente
})
export class SegnalazionePagamentoService {
  // URL base dell'API, definito nell'ambiente
  private apiUrl = `${environment.apiUrl}/gestore/segnalazioniPagamento`;

  // Costruttore per iniettare il servizio HttpClient
  constructor(private http: HttpClient) {}

  /**
   * Risolve una segnalazione di pagamento.
   * @param idSegnalazione L'ID della segnalazione da risolvere.
   * @param dettagliRisoluzione I dettagli relativi alla risoluzione della segnalazione.
   * @returns Un Observable con la segnalazione aggiornata.
   */
  risolvereSegnalazione(
    idSegnalazione: number,
    dettagliRisoluzione: string,
  ): Observable<SegnalazionePagamento> {
    // Parametri da passare alla richiesta PATCH
    const params = { dettagliRisoluzione };

    // Effettua una richiesta PATCH per risolvere la segnalazione
    return this.http.patch<SegnalazionePagamento>(
      `${this.apiUrl}/risolvi/${idSegnalazione}`, // URL dell'endpoint
      null, // Corpo della richiesta (null poiché non richiesto)
      { params }, // Parametri aggiuntivi della richiesta
    );
  }

  /**
   * Prende in carico una segnalazione di pagamento.
   * @param idSegnalazione L'ID della segnalazione da prendere in carico.
   * @returns Un Observable con la segnalazione aggiornata.
   */
  prendiInCaricoSegnalazione(idSegnalazione: number): Observable<any> {
    // Effettua una richiesta PATCH per aggiornare lo stato della segnalazione
    return this.http.patch<any>(
      `${this.apiUrl}/prendiInCarico/${idSegnalazione}`, // URL dell'endpoint
      null, // Corpo della richiesta (null poiché non richiesto)
    );
  }

  /**
   * Recupera tutte le segnalazioni.
   * @returns Un Observable con la lista di tutte le segnalazioni.
   */
  getAllSegnalazioni(): Observable<any[]> {
    // Effettua una richiesta GET per ottenere tutte le segnalazioni
    return this.http.get<any[]>(this.apiUrl);
  }
}
