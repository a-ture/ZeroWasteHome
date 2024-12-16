package it.unisa.zwhbackend.service.gestioneProdotti;

import it.unisa.zwhbackend.annotations.ExcludeGeneratedFromCodeCoverage;
import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.repository.PossiedeInDispensaRepository;
import it.unisa.zwhbackend.model.repository.PossiedeInFrigoRepository;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
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
 * @author Marco Meglio, Ferdinando Ranieri, Alessandra Trotta
 */
@Service
public class GestioneProdottoService implements ProdottoService {
  private final String PRODOTTO_SENZA_CODICE = "100000000000000";
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
   * @param categoria la categoria del prodotto
   * @return il prodotto appena creato e salvato nel database
   */
  public Prodotto inserisciProdotto(
      String nomeProdotto, String dataScadenza, String codiceBarre, List<String> categoria) {
    // Crea una nuova istanza di Prodotto
    Prodotto prodotto = new Prodotto(nomeProdotto, codiceBarre, categoria);
    return prodottoRepository.save(prodotto); // Salva il prodotto nel database
  }

  /**
   * Aggiunge un prodotto al frigo di un utente.
   *
   * <p>Se il prodotto non esiste nel sistema, viene creato. Se esiste già una relazione tra il
   * prodotto e l'utente per la stessa data di scadenza, la quantità viene aggiornata; altrimenti,
   * viene creata una nuova relazione.
   *
   * @param nomeProdotto il nome del prodotto; non può essere nullo e deve avere una lunghezza
   *     massima di 50 caratteri
   * @param dataScadenza la data di scadenza del prodotto, in formato "yyyy-MM-dd"; non può essere
   *     nulla
   * @param codiceBarre il codice a barre del prodotto; non può essere nullo e deve contenere da 8 a
   *     16 cifre
   * @param quantita la quantità del prodotto da aggiungere; deve essere un numero positivo maggiore
   *     di zero
   * @param email l'email dell'utente a cui aggiungere il prodotto; non può essere nulla
   * @param categoria una lista di categorie associate al prodotto; può essere vuota
   * @param img l'url dell'immagine associata al prodotto
   * @return il prodotto aggiunto o aggiornato
   * @throws IllegalArgumentException se i parametri non rispettano i vincoli di validazione
   * @throws IllegalStateException se l'utente non esiste nel sistema
   */
  public Prodotto aggiungiProdottoFrigo(
      String nomeProdotto,
      String dataScadenza,
      String codiceBarre,
      int quantita,
      String email,
      List<String> categoria,
      String img) {
    // Validazione dei campi

    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    try {
      LocalDate date = LocalDate.parse(dataScadenza, inputFormatter);
      dataScadenza = date.format(outputFormatter);
    } catch (DateTimeParseException e) {
      System.out.println("formato data non valido");
    }

    System.out.println("la data di scadenza è: _" + dataScadenza + "_");

    validaCodiceBarre(codiceBarre);
    validaNomeProdotto(nomeProdotto);
    validaQuantita(quantita);
    validaDataScadenza(dataScadenza);
    // è un segnale che si tratta di un prodotto senza codice a barre
    if (codiceBarre.equals(PRODOTTO_SENZA_CODICE)) {
      // Trova il codice a 15 cifre con il valore più alto
      String temp = prodottoRepository.findMaxCodiceBarreWith15Digits();

      // Se non esiste, inizializza con il valore di default
      if (temp == null) {
        temp = PRODOTTO_SENZA_CODICE;
      }

      // Incrementa il valore numerico del codice a barre
      long nuovoCodiceBarre = Long.parseLong(temp) + 1;

      // Aggiorna il codice a barre come stringa
      codiceBarre = String.valueOf(nuovoCodiceBarre);
      System.out.println("Nuovo codice a barre generato: " + codiceBarre);
    }

    // Controlla se il prodotto esiste già nel database
    Optional<Prodotto> prodottoOptional = prodottoRepository.findByCodiceBarre(codiceBarre);
    Prodotto prodotto;
    if (prodottoOptional.isEmpty()) {

      // Se il prodotto non esiste, lo creiamo
      prodotto = inserisciProdotto(nomeProdotto, dataScadenza, codiceBarre, categoria);

      if (prodotto != null) {
        prodotto.setImg(img);
      }

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
      prodotto = new Prodotto(nomeProdotto, codiceBarre, categoria);
    }

    System.out.println(prodotto);
    return prodotto; // Ritorna il prodotto (sia nuovo che trovato)
  }

  /**
   * Valida il codice a barre fornito. Il codice deve essere composto esclusivamente da cifre e
   * avere una lunghezza compresa tra 8 e 16 caratteri.
   *
   * @param codiceBarre il codice a barre da validare
   * @throws IllegalArgumentException se il codice a barre è nullo, non contiene solo cifre o non ha
   *     una lunghezza compresa tra 8 e 16 caratteri
   */
  private void validaCodiceBarre(String codiceBarre) {
    if (codiceBarre == null || !codiceBarre.matches("^[0-9]{8,16}$")) {
      throw new IllegalArgumentException(
          "Il codice deve avere una lunghezza minima di 8 caratteri, massima di 16 e deve contenere solo cifre.");
    }
  }

  /**
   * Valida il nome del prodotto fornito. Il nome deve essere composto esclusivamente da lettere
   * dell'alfabeto e avere una lunghezza massima di 50 caratteri.
   *
   * @param nomeProdotto il nome del prodotto da validare
   * @throws IllegalArgumentException se il nome del prodotto è nullo, contiene caratteri non
   *     alfabetici o supera la lunghezza di 50 caratteri
   */
  private void validaNomeProdotto(String nomeProdotto) {
    if (nomeProdotto == null || !nomeProdotto.matches("^[a-zA-Z0-9\\s]{1,50}$")) {
      throw new IllegalArgumentException(
          "La lunghezza massima per questo campo è 50 caratteri e deve contenere solo lettere dell'alfabeto.");
    }
  }

  /**
   * Valida la data di scadenza fornita. La data deve essere nel formato gg/mm/aaaa.
   *
   * @param dataScadenza la data di scadenza da validare
   * @throws IllegalArgumentException se la data è nulla o non rispetta il formato richiesto
   */
  private void validaDataScadenza(String dataScadenza) {
    if (dataScadenza == null
        || !dataScadenza.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
      throw new IllegalArgumentException("La Data deve essere del formato gg/mm/aaaa.");
    }
  }

  /**
   * Valida la quantità fornita. La quantità deve essere un numero positivo maggiore di zero.
   *
   * @param quantita la quantità da validare
   * @throws IllegalArgumentException se la quantità è minore o uguale a zero
   */
  private void validaQuantita(int quantita) {
    if (quantita <= 0) {
      throw new IllegalArgumentException(
          "La Quantità deve essere un numero positivo maggiore di zero.");
    }
  }

  /**
   * Restituisce tutti i prodotti presenti nella dispensa di un utente specifico.
   *
   * <p>Questo metodo recupera tutte le relazioni {@link PossiedeInDispensa} associate all'utente
   * identificato dall'email fornita, trasformandole in una lista di oggetti {@link
   * ProdottoRequestDTO} contenenti i dettagli del prodotto, la quantità e la scadenza.
   *
   * @param email l'email dell'utente di cui visualizzare i prodotti nella dispensa
   * @return una lista di {@link ProdottoRequestDTO} rappresentanti i prodotti nella dispensa
   * @throws IllegalStateException se l'utente con l'email fornita non esiste
   * @throws RuntimeException se si verifica un errore durante il recupero dei dati
   */
  public List<ProdottoRequestDTO> visualizzaProdottiDispensa(String email) {
    try {
      Utente utente = utenteRepository.findByEmail(email);
      if (utente == null) {
        throw new IllegalStateException("Utente con email " + email + " non trovato");
      }

      List<PossiedeInDispensa> relazioni = possiedeInDispensaRepository.findByUtente(utente);

      List<ProdottoRequestDTO> prodottiInDispensa = new ArrayList<>();
      for (PossiedeInDispensa relazione : relazioni) {
        Prodotto prodotto = relazione.getProdotto();
        prodottiInDispensa.add(
            new ProdottoRequestDTO(
                prodotto.getCodiceBarre(),
                prodotto.getName(),
                prodotto.getImg(),
                relazione.getDataScadenza(),
                relazione.getQuantita(),
                relazione.getUtente().getEmail()));
      }
      return prodottiInDispensa;
    } catch (NoSuchElementException e) {
      return new ArrayList<>();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Errore durante il recupero dei prodotti dalla dispensa", e);
    }
  }

  /**
   * Restituisce tutti i prodotti presenti nel frigo di un utente specifico.
   *
   * <p>Questo metodo recupera tutte le relazioni {@link PossiedeInFrigo} associate all'utente
   * identificato dall'email fornita, trasformandole in una lista di oggetti {@link
   * ProdottoRequestDTO} contenenti i dettagli del prodotto, la quantità e la scadenza.
   *
   * @param email l'email dell'utente di cui visualizzare i prodotti nel frigo
   * @return una lista di {@link ProdottoRequestDTO} rappresentanti i prodotti nel frigo
   * @throws IllegalStateException se l'utente con l'email fornita non esiste
   * @throws RuntimeException se si verifica un errore durante il recupero dei dati
   */
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public List<ProdottoRequestDTO> visualizzaProdottiFrigo(String email) {
    try {
      Utente utente = utenteRepository.findByEmail(email);
      if (utente == null) {
        throw new IllegalStateException("Utente con email " + email + " non trovato");
      }

      List<PossiedeInFrigo> relazioni = possiedeInFrigoRepository.findByUtente(utente);

      List<ProdottoRequestDTO> prodottiInFrigo = new ArrayList<>();
      for (PossiedeInFrigo relazione : relazioni) {
        Prodotto prodotto = relazione.getProdotto();
        prodottiInFrigo.add(
            new ProdottoRequestDTO(
                prodotto.getCodiceBarre(),
                prodotto.getName(),
                prodotto.getImg(),
                relazione.getDataScadenza(),
                relazione.getQuantita(),
                relazione.getUtente().getEmail()));
      }
      return prodottiInFrigo;
    } catch (NoSuchElementException e) {
      return new ArrayList<>();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Errore durante il recupero dei prodotti dal frigo", e);
    }
  }

  /**
   * Esegue una ricerca di prodotti basata su un criterio parziale del nome e filtra i risultati in
   * base ai prodotti presenti nella dispensa dell'utente specificato.
   *
   * <p>Il metodo verifica che i parametri siano validi, recupera i prodotti il cui nome contiene la
   * stringa fornita (case-insensitive) e filtra i risultati in base ai prodotti presenti nella
   * dispensa dell'utente. I prodotti risultanti vengono trasformati in oggetti {@link
   * ProdottoRequestDTO}.
   *
   * @param emailUtente l'email dell'utente per cui eseguire la ricerca
   * @param nomeProdotto il criterio di ricerca parziale sul nome del prodotto
   * @return una lista di {@link ProdottoRequestDTO} contenente i prodotti che soddisfano i criteri
   *     di ricerca
   * @throws IllegalArgumentException se uno dei parametri è nullo o vuoto
   * @throws NoSuchElementException se non viene trovato alcun prodotto corrispondente al criterio
   *     di ricerca
   */
  @Override
  public List<ProdottoRequestDTO> RicercaPerNome(String emailUtente, String nomeProdotto) {
    // Verifica che i parametri non siano vuoti o nulli
    if (emailUtente == null || emailUtente.trim().isEmpty()) {
      throw new IllegalArgumentException("L'email dell'utente non può essere vuota.");
    }
    if (nomeProdotto == null || nomeProdotto.trim().isEmpty()) {
      throw new IllegalArgumentException("Il nome del prodotto non può essere vuoto.");
    }

    // Recupera i prodotti filtrati per nome
    List<Prodotto> prodottiFiltrati =
        prodottoRepository.findByNameContainingIgnoreCase(nomeProdotto);

    // Gestisce il caso in cui non vengano trovati prodotti
    if (prodottiFiltrati == null || prodottiFiltrati.isEmpty()) {
      throw new NoSuchElementException("Nessun prodotto trovato");
    }

    Utente utente = new Utente();
    utente.setEmail(emailUtente);
    // Recupera i record di PossiedeInDispensa per l'utente
    List<PossiedeInDispensa> prodottiInDispensa = possiedeInDispensaRepository.findByUtente(utente);

    // Filtra i prodotti nella dispensa in base ai risultati del repository
    List<PossiedeInDispensa> prodottiDispensaFiltrati =
        prodottiInDispensa.stream()
            .filter(
                record ->
                    prodottiFiltrati.stream()
                        .anyMatch(
                            prodotto ->
                                prodotto
                                    .getCodiceBarre()
                                    .equals(record.getProdotto().getCodiceBarre())))
            .collect(Collectors.toList());

    // Trasforma i record filtrati in una lista di ProdottoRequestDTO
    return prodottiDispensaFiltrati.stream()
        .map(
            record -> {
              Prodotto prodotto = record.getProdotto();
              ProdottoRequestDTO dto = new ProdottoRequestDTO();
              dto.setCodiceBarre(prodotto.getCodiceBarre());
              dto.setNomeProdotto(prodotto.getName());
              dto.setDataScadenza(record.getDataScadenza());
              dto.setQuantità(record.getQuantita());
              dto.setImg(prodotto.getImg());
              return dto;
            })
        .collect(Collectors.toList());
  }
}
