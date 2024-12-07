package it.unisa.zwhbackend.service.gestioneProdotti;

import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.repository.PossiedeInDispensaRepository;
import it.unisa.zwhbackend.model.repository.PossiedeInFrigoRepository;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La classe {@code ProdottoService} gestisce la logica di business per i prodotti. Fornisce metodi
 * per la gestione dei prodotti nel sistema, come l'inserimento di nuovi prodotti e l'aggiunta di
 * prodotti al frigo di un utente.
 *
 * <p>Il servizio interagisce con i repository per eseguire operazioni CRUD sugli oggetti {@link
 * Prodotto}, {@link Utente} e {@link PossiedeInFrigo}.
 *
 * @author Marco Meglio, Ferdinando Ranieri
 */
@Service
public class GestioneProdottoService implements ProdottoService {

  // Dichiarazione dei repository utilizzati per interagire con il database
  private final ProdottoRepository prodottoRepository;
  private final PossiedeInFrigoRepository possiedeInFrigoRepository;
  private final PossiedeInDispensaRepository possiedeInDispensaRepository;
  private final UtenteRepository utenteRepository;

  /**
   * Iniezione delle dipendenze tramite costruttore.
   *
   * @param prodottoRepository il repository per i prodotti
   * @param possiedeInFrigoRepository il repository per la relazione tra prodotto e utente
   * @param possiedeInDispensaRepository il repository per la relazione tra prodotto e utente
   * @param utenteRepository il repository per gli utenti
   */
  @Autowired
  public GestioneProdottoService(
      ProdottoRepository prodottoRepository,
      PossiedeInFrigoRepository possiedeInFrigoRepository,
      PossiedeInDispensaRepository possiedeInDispensaRepository,
      UtenteRepository utenteRepository) {
    this.prodottoRepository = prodottoRepository;
    this.possiedeInFrigoRepository = possiedeInFrigoRepository;
    this.possiedeInDispensaRepository = possiedeInDispensaRepository;
    this.utenteRepository = utenteRepository;
  }

  /**
   * Inserisce un nuovo prodotto nel sistema.
   *
   * <p>Questo metodo crea un nuovo oggetto {@link Prodotto} con i dati forniti e lo salva nel
   * database tramite il repository {@link ProdottoRepository}.
   *
   * @param nomeProdotto il nome del prodotto
   * @param dataScadenza la data di scadenza del prodotto
   * @param codiceBarre il codice a barre del prodotto
   * @return il prodotto appena creato e salvato nel database
   */
  public Prodotto inserisciProdotto(String nomeProdotto, String dataScadenza, String codiceBarre) {
    // Crea una nuova istanza di Prodotto
    Prodotto prodotto = new Prodotto(nomeProdotto, codiceBarre);
    return prodottoRepository.save(prodotto); // Salva il prodotto nel database
  }

  /**
   * Aggiunge un prodotto al frigo di un utente.
   *
   * <p>Questo metodo gestisce l'aggiunta di un prodotto al frigo di un utente specifico. Se il
   * prodotto non esiste già nel sistema, viene creato e salvato. Se la relazione tra il prodotto e
   * l'utente esiste già, la quantità viene aggiornata; altrimenti, viene creata una nuova
   * relazione.
   *
   * <p>Le validazioni includono:
   *
   * <ul>
   *   <li>Il codice a barre deve essere un numero intero di massimo 8 cifre
   *   <li>Il nome del prodotto deve contenere solo lettere (massimo 50 caratteri)
   *   <li>La data di scadenza deve essere nel formato "gg/mm/aaaa"
   *   <li>La quantità deve essere un numero positivo maggiore di zero
   * </ul>
   *
   * @param nomeProdotto il nome del prodotto
   * @param dataScadenza la data di scadenza del prodotto (es. "01/01/2024")
   * @param codiceBarre il codice a barre del prodotto (es. "12345678")
   * @param quantita la quantità del prodotto da aggiungere
   * @param email l'id dell'utente a cui il prodotto sarà aggiunto
   * @return il prodotto (nuovo o esistente) associato all'utente
   * @throws IllegalArgumentException se uno dei parametri non è valido
   * @throws IllegalStateException se l'utente specificato non è trovato
   * @throws RuntimeException in caso di errore non gestito durante l'aggiunta
   */
  public Prodotto aggiungiProdottoFrigo(
      String nomeProdotto, String dataScadenza, String codiceBarre, int quantita, String email) {
    // Validazione dei campi
    validaCodiceBarre(codiceBarre);
    validaNomeProdotto(nomeProdotto);
    validaQuantita(quantita);
    validaDataScadenza(dataScadenza);

    // Controlla se il prodotto esiste già nel database
    Optional<Prodotto> prodottoOptional = prodottoRepository.findByCodiceBarre(codiceBarre);
    Prodotto prodotto;
    if (prodottoOptional.isEmpty()) {

      // Se il prodotto non esiste, lo creiamo
      prodotto = inserisciProdotto(nomeProdotto, dataScadenza, codiceBarre);
      prodottoRepository.saveAndFlush(
          prodotto); // Inserisce nel DB e lo rende persistente immediatamente
    } else {
      prodotto = prodottoOptional.get(); // Utilizza il prodotto esistente se trovato
    }

    Utente utente = utenteRepository.findByEmail(email);
    if (utente == null) {
      throw new IllegalStateException(
          "Utente con ID "
              + email
              + " non trovato"); // Lancia un'eccezione se l'utente non è trovato
    }

    // Verifica se esiste già una relazione tra l'utente e il prodotto nel frigo
    System.out.println("Ora controllo che ci sia già una relazione");
    // Recupera tutte le relazioni per utente e prodotto
    List<PossiedeInFrigo> relazioni =
        possiedeInFrigoRepository.findByUtenteAndProdotto(utente, prodotto);

    if (relazioni.isEmpty()) {
      // Nessuna relazione trovata, crea una nuova
      System.out.println("Relazione non trovata");
      PossiedeInFrigo nuovaRelazione =
          new PossiedeInFrigo(utente, prodotto, quantita, dataScadenza);
      possiedeInFrigoRepository.save(nuovaRelazione);
      System.out.println("Relazione creata");
    } else {
      boolean relazioneAggiornata = false;

      // Itera su tutte le relazioni esistenti e confronta la data di scadenza
      for (PossiedeInFrigo relazione : relazioni) {
        // Se la data di scadenza coincide, aggiorna la quantità
        if (relazione.getDataScadenza().equals(dataScadenza)) {
          relazione.setQuantita(relazione.getQuantita() + quantita); // Aggiungi la quantità
          possiedeInFrigoRepository.save(relazione); // Salva la relazione aggiornata
          System.out.println(
              "Quantità aggiornata per relazione con data di scadenza: " + dataScadenza);
          relazioneAggiornata = true;
          break; // Esce dal ciclo dopo aver aggiornato la relazione
        }
      }

      // Se nessuna relazione è stata aggiornata (nessuna corrispondenza con la data di scadenza),
      // crea una nuova relazione
      if (!relazioneAggiornata) {
        PossiedeInFrigo nuovaRelazione =
            new PossiedeInFrigo(utente, prodotto, quantita, dataScadenza);
        possiedeInFrigoRepository.save(nuovaRelazione); // Salva la nuova relazione
        System.out.println("Relazione creata con data di scadenza diversa.");
      }
    }

    // restituisce il prodotto che sarebbe dovuto essere inserito
    if (prodotto == null) {
      prodotto = new Prodotto(nomeProdotto, codiceBarre);
    }

    System.out.println(prodotto);
    return prodotto; // Ritorna il prodotto (sia nuovo che trovato)
  }

  private void validaCodiceBarre(String codiceBarre) {
    if (codiceBarre == null || !codiceBarre.matches("^[0-9]{1,8}$")) {
      throw new IllegalArgumentException(
          "Il codice deve avere una lunghezza massima di 8 caratteri e deve contenere solo cifre.");
    }
  }

  private void validaNomeProdotto(String nomeProdotto) {
    if (nomeProdotto == null || !nomeProdotto.matches("^[a-zA-Z]{1,50}$")) {
      throw new IllegalArgumentException(
          "La lunghezza massima per questo campo è 50 caratteri e deve contenere solo lettere dell'alfabeto.");
    }
  }

  private void validaDataScadenza(String dataScadenza) {
    if (dataScadenza == null
        || !dataScadenza.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
      throw new IllegalArgumentException("La Data deve essere del formato gg/mm/aa.");
    }
  }

  private void validaQuantita(int quantita) {
    if (quantita <= 0) {
      throw new IllegalArgumentException(
          "La Quantità deve essere un numero positivo maggiore di zero.");
    }
  }

  /**
   * Restituisce tutti i prodotti presenti nella dispensa di un utente.
   *
   * <p>Questo metodo recupera tutte le relazioni {@link PossiedeInDispensa} per un utente
   * specificato e restituisce i prodotti associati a quelle relazioni.
   *
   * @param email l'id dell'utente di cui visualizzare i prodotti nella dispensa
   * @return una lista di {@link Prodotto} presenti nella dispensa dell'utente
   * @throws RuntimeException se si verifica un errore durante il recupero dei dati
   */
  public List<Prodotto> visualizzaProdottiDispensa(String email) {
    try {
      // Recupera l'utente con l'id specificato
      Utente utente = utenteRepository.findByEmail(email);
      if (utente == null) {
        throw new IllegalStateException("Utente con ID " + email + " non trovato");
      }

      // Recupera tutte le relazioni tra l'utente e i prodotti nella dispensa
      List<PossiedeInDispensa> relazioni = possiedeInDispensaRepository.findByUtente(utente);

      // Estrae i prodotti dalle relazioni
      List<Prodotto> prodottiInDispensa = new ArrayList<>();
      for (PossiedeInDispensa relazione : relazioni) {
        prodottiInDispensa.add(relazione.getProdotto()); // Aggiunge il prodotto alla lista
      }
      if (prodottiInDispensa.isEmpty()) {
        throw new NoSuchElementException("Nessun prodotto in Dispensa.");
      }
      return prodottiInDispensa; // Ritorna la lista dei prodotti

    } catch (NoSuchElementException e) {
      // Gestisce il caso in cui non ci siano prodotti in dispensa
      return new ArrayList<>(); // Ritorna una lista vuota in caso di errore specifico
    } catch (Exception e) {
      // Gestisce eventuali errori
      e.printStackTrace();
      throw new RuntimeException("Errore durante il recupero dei prodotti dalla dispensa", e);
    }
  }
}
