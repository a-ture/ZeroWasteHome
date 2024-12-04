package it.unisa.zwhbackend.service.gestioneUtenteStandard.registazione;

import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio per la gestione della registrazione degli utenti. Fornisce la logica
 * di business per registrare un nuovo utente nel sistema.
 *
 * @author Alessia Ture
 */
@Service
public class GestioneRegistrazioneService implements RegistrazioneService {

  private final UtenteRepository utenteRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Costruttore della classe. Inietta il repository degli utenti necessario per le operazioni di
   * persistenza.
   *
   * @param utenteRepository il repository per la gestione degli utenti
   * @param passwordEncoder l'encoder per la gestione delle password degli utenti
   */
  @Autowired
  public GestioneRegistrazioneService(
      UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
    this.utenteRepository = utenteRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Registra un nuovo utente nel sistema.
   *
   * <p>Verifica che i dati forniti siano validi e che l'email non sia già presente nel database. Se
   * le verifiche passano, salva l'utente nel database.
   *
   * @param utente l'utente da registrare
   * @return l'utente registrato
   * @throws IllegalArgumentException se i dati dell'utente sono nulli o se l'email è già registrata
   */
  @Override
  public Utente registrazione(Utente utente) {
    if (utente == null) {
      throw new IllegalArgumentException("Dati utente non validi");
    }

    if (utenteRepository.existsByEmail(utente.getEmail())) {
      throw new IllegalArgumentException("Email già registrata");
    }

    utente.setPassword(passwordEncoder.encode(utente.getPassword()));

    return utenteRepository.save(utente);
  }
}
