package it.unisa.zwhbackend.service.registazione;

import it.unisa.zwhbackend.model.entity.Utente;

/**
 * Interfaccia che fornisce i metodi per la logica di registrazione degli utenti
 *
 * @author Alessia Ture
 */
public interface RegistrazioneService {

  Utente registrazione(Utente utente);
}
