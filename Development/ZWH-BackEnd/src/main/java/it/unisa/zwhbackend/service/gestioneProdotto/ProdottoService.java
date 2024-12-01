package it.unisa.zwhbackend.service.gestioneProdotto;

import it.unisa.zwhbackend.model.entity.PossiedeInFrigo;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.PossiedeInFrigoRepository;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La classe {@code ProdottoService} gestisce la logica di business per i prodotti.
 * Fornisce metodi per la gestione dei prodotti nel sistema, come l'inserimento di nuovi
 * prodotti e l'aggiunta di prodotti al frigo di un utente.
 *
 * <p>Il servizio interagisce con i repository per eseguire operazioni CRUD sugli oggetti
 * {@link Prodotto}, {@link Utente} e {@link PossiedeInFrigo}.
 *
 * @author Marco Meglio
 */
@Service
public class ProdottoService {

  // Dichiarazione dei repository utilizzati per interagire con il database
  private final ProdottoRepository prodottoRepository;
  private final PossiedeInFrigoRepository possiedeInFrigoRepository;
  private final UtenteRepository utenteRepository;

  /**
   * Iniezione delle dipendenze tramite costruttore.
   * @param prodottoRepository il repository per i prodotti
   * @param possiedeInFrigoRepository il repository per la relazione tra prodotto e utente
   * @param utenteRepository il repository per gli utenti
   */
  @Autowired
  public ProdottoService(
          ProdottoRepository prodottoRepository,
          PossiedeInFrigoRepository possiedeInFrigoRepository,
          UtenteRepository utenteRepository) {
    this.prodottoRepository = prodottoRepository;
    this.possiedeInFrigoRepository = possiedeInFrigoRepository;
    this.utenteRepository = utenteRepository;
  }

  /**
   * Inserisce un nuovo prodotto nel sistema.
   *
   * <p>Questo metodo crea un nuovo oggetto {@link Prodotto} con i dati forniti
   * e lo salva nel database tramite il repository {@link ProdottoRepository}.
   *
   * @param nomeProdotto il nome del prodotto
   * @param dataScadenza la data di scadenza del prodotto
   * @param codiceBarre il codice a barre del prodotto
   * @return il prodotto appena creato e salvato nel database
   */
  public Prodotto inserisciProdotto(String nomeProdotto, String dataScadenza, String codiceBarre) {
    // Crea una nuova istanza di Prodotto
    Prodotto prodotto = new Prodotto(nomeProdotto, dataScadenza, codiceBarre);
    System.out.println(prodotto);  // Log per il prodotto creato
    System.out.println("-----------Prodotto creato");
    return prodottoRepository.save(prodotto);  // Salva il prodotto nel database
  }

  /**
   * Aggiunge un prodotto al frigo di un utente.
   *
   * <p>Questo metodo verifica se il prodotto esiste già nel sistema. Se il prodotto
   * non esiste, lo crea. Successivamente, viene verificata la relazione tra l'utente
   * e il prodotto nel frigo. Se la relazione esiste, viene aggiornata la quantità,
   * altrimenti viene creata una nuova relazione {@link PossiedeInFrigo}.
   *
   * @param nomeProdotto il nome del prodotto
   * @param dataScadenza la data di scadenza del prodotto
   * @param codiceBarre il codice a barre del prodotto
   * @param quantita la quantità del prodotto da aggiungere
   * @return il prodotto (sia nuovo che trovato)
   * @throws RuntimeException se si verifica un errore durante l'aggiunta del prodotto al frigo
   */
  public Prodotto aggiungiProdottoFrigo(
          String nomeProdotto, String dataScadenza, String codiceBarre, int quantita) {
    try {
      System.out.println("Iniziamo...");

      // Controlla se il prodotto esiste già nel database
      Optional<Prodotto> prodottoOptional = prodottoRepository.findByCodiceBarre(codiceBarre);
      Prodotto prodotto;
      if (prodottoOptional.isEmpty()) {
        System.out.println("Prodotto non trovato");

        // Se il prodotto non esiste, lo creiamo
        prodotto = inserisciProdotto(nomeProdotto, dataScadenza, codiceBarre);
        System.out.println("Creazione prodotto...");
        prodottoRepository.saveAndFlush(prodotto);  // Inserisce nel DB e lo rende persistente immediatamente
      } else {
        System.out.println("Prodotto trovato");
        prodotto = prodottoOptional.get();  // Utilizza il prodotto esistente se trovato
      }

      // Recupera l'utente con ID 1
      // Per il momento recupero sempre lo stesso utente , questa dinamica dovrà cambiare
      Optional<Utente> utenteOptional = utenteRepository.findById(1L);
      if (utenteOptional.isEmpty()) {
        throw new IllegalStateException("Utente con ID 1 non trovato");  // Lancia un'eccezione se l'utente non è trovato
      }
      Utente utente = utenteOptional.get();  // Ottiene l'utente trovato

      // Verifica se esiste già una relazione tra l'utente e il prodotto nel frigo
      System.out.println("Ora controllo che ci sia già una relazione");
      Optional<PossiedeInFrigo> relazioneOptional =
              possiedeInFrigoRepository.findByUtenteAndProdotto(utente, prodotto);
      System.out.println("----fatto");

      if (relazioneOptional.isPresent()) {
        // Se la relazione esiste, aggiorna la quantità
        System.out.println("Relazione trovata");

        PossiedeInFrigo relazione = relazioneOptional.get();
        relazione.setQuantita(relazione.getQuantita() + quantita);  // Aggiunge la quantità alla relazione esistente
        possiedeInFrigoRepository.save(relazione);  // Salva la relazione aggiornata
        System.out.println("Quantità aggiornata");
      } else {
        System.out.println("Relazione non trovata");
        // Se la relazione non esiste, crea una nuova relazione
        PossiedeInFrigo nuovaRelazione = new PossiedeInFrigo(utente, prodotto, quantita);
        possiedeInFrigoRepository.save(nuovaRelazione);  // Salva la nuova relazione
        System.out.println("Relazione creata");
      }

      return prodotto;  // Ritorna il prodotto (sia nuovo che trovato)
    } catch (Exception e) {
      // Gestisce eventuali errori
      System.err.println("Errore durante l'aggiunta del prodotto al frigo: " + e.getMessage());
      e.printStackTrace();  // Stampa lo stack trace per diagnosticare meglio l'errore
      throw new RuntimeException(
              "Errore durante l'aggiunta del prodotto al frigo", e);  // Rilancia l'eccezione per permettere una gestione superiore
    }
  }
}
