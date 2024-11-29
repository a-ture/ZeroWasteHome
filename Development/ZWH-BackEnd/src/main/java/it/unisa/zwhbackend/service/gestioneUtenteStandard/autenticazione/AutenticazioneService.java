package it.unisa.zwhbackend.service.gestioneUtenteStandard.autenticazione;

/**
 * Interfaccia che fornisce i metodi per la logica di autenticazione degli utenti
 *
 * @author Marco Renella
 */
public interface AutenticazioneService {
  String login(String email, String password) throws Exception; // Restituisce un token JWT
}
