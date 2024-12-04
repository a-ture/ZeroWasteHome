package it.unisa.zwhbackend.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

/**
 * Implementa un provider manuale per la gestione dei token JWT (JSON Web Token). Questo provider
 * utilizza l'email dell'utente come identificativo e include i ruoli dell'utente nel payload del
 * token.
 *
 * <p>La firma del token viene eseguita utilizzando l'algoritmo HMAC-SHA512 con una chiave segreta
 * predefinita. La classe gestisce la creazione, la validazione e l'estrazione delle informazioni
 * dal token JWT.
 *
 * <p>Utilizza il formato standard JWT con un header base64-encoded, un payload base64-encoded
 * contenente l'email dell'utente, i suoi ruoli e un timestamp di scadenza, e una firma per la
 * validazione.
 *
 * @author Marco Renella
 */
@Component
public class JwtManualProvider {

  /** La chiave segreta utilizzata per la firma del token (deve essere mantenuta sicura). */
  private static final String SECRET_KEY = "secureKey123secureKey123secureKey123secureKey123";

  /** L'algoritmo utilizzato per la firma del token. */
  private static final String ALGORITHM = "HmacSHA512";

  /**
   * Genera un token JWT per un dato utente, utilizzando l'email come identificativo e includendo i
   * ruoli dell'utente.
   *
   * <p>Il token contiene un header, un payload e una firma. L'header specifica l'algoritmo di firma
   * e il tipo di token (JWT). Il payload include l'email dell'utente (come subject), il timestamp
   * di emissione (iat), la data di scadenza (exp) e i ruoli dell'utente. La firma garantisce che il
   * token non sia stato alterato.
   *
   * @param email l'indirizzo email dell'utente (subject del token).
   * @param roles una lista di ruoli associati all'utente.
   * @return una stringa contenente il token JWT generato, formato da header, payload e firma
   *     separati da punti.
   */
  public String generateToken(String email, List<String> roles) {
    // Header
    String header =
        Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString("{\"alg\":\"HS512\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));

    // Payload
    long now = System.currentTimeMillis() / 1000; // Tempo corrente in secondi
    long exp = now + (60 * 60); // Scadenza del token (1 ora)
    String payload =
        String.format(
            "{\"sub\":\"%s\",\"iat\":%d,\"exp\":%d,\"roles\":%s}",
            email, now, exp, rolesToJson(roles));
    String encodedPayload =
        Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(payload.getBytes(StandardCharsets.UTF_8));

    // Signature
    String signature = sign(header + "." + encodedPayload);

    return header + "." + encodedPayload + "." + signature;
  }

  /**
   * Verifica la validità di un token JWT.
   *
   * <p>Il token è valido se ha il formato corretto (tre parti separate da punti), se la firma è
   * corretta e se la data di scadenza non è stata superata.
   *
   * @param token il token JWT da validare.
   * @return {@code true} se il token è valido, altrimenti {@code false}.
   */
  public boolean validateToken(String token) {
    try {
      String[] parts = token.split("\\.");
      if (parts.length != 3) return false; // Deve contenere 3 parti (header, payload, signature)

      String headerPayload = parts[0] + "." + parts[1];
      String signature = parts[2];

      // Verifica la firma
      if (!sign(headerPayload).equals(signature)) return false;

      // Verifica la scadenza
      String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
      long exp = Long.parseLong(payload.replaceAll(".*\"exp\":(\\d+).*", "$1"));
      return System.currentTimeMillis() / 1000 < exp; // Controlla se la scadenza è valida
    } catch (Exception e) {
      return false; // In caso di errore, il token non è valido
    }
  }

  /**
   * Estrae l'email (o altro identificativo) dal token JWT.
   *
   * <p>Il metodo decodifica il payload del token e recupera il valore del campo "sub", che
   * rappresenta l'email dell'utente.
   *
   * @param token il token JWT da cui estrarre l'email.
   * @return l'email estratta dal token, o {@code null} se il token è invalido o il campo non
   *     esiste.
   */
  public String getEmail(String token) {
    try {
      String payload =
          new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
      return payload.replaceAll(".*\"sub\":\"([^\"]+)\".*", "$1"); // Estrae l'email
    } catch (Exception e) {
      return null; // Restituisce null se il token è invalido
    }
  }

  /**
   * Estrae i ruoli dell'utente dal token JWT.
   *
   * <p>Il metodo decodifica il payload del token e recupera il campo "roles", che contiene una
   * lista di ruoli associati all'utente. Restituisce una lista di stringhe contenente i ruoli.
   *
   * @param token il token JWT da cui estrarre i ruoli.
   * @return una lista di ruoli estratta dal token, o {@code null} se il token è invalido.
   */
  public List<String> getRoles(String token) {
    try {
      String payload =
          new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
      return List.of(payload.replaceAll(".*\"roles\":\\[(.*)\\].*", "$1").split(",")).stream()
          .map(role -> role.replaceAll("\"", "").trim()) // Rimuove virgolette e spazi
          .collect(Collectors.toList());
    } catch (Exception e) {
      return null; // Restituisce null se il token è invalido
    }
  }

  /**
   * Converte una lista di ruoli in una stringa JSON.
   *
   * <p>Ogni ruolo viene racchiuso tra virgolette e separato da virgole, formando un array JSON.
   *
   * @param roles la lista dei ruoli da convertire.
   * @return una stringa che rappresenta i ruoli in formato JSON.
   */
  private String rolesToJson(List<String> roles) {
    StringBuilder rolesJson = new StringBuilder("[");
    for (int i = 0; i < roles.size(); i++) {
      rolesJson.append("\"").append(roles.get(i)).append("\"");
      if (i < roles.size() - 1) rolesJson.append(",");
    }
    rolesJson.append("]");
    return rolesJson.toString();
  }

  /**
   * Firma i dati utilizzando l'algoritmo HMAC-SHA512 con una chiave segreta.
   *
   * <p>La firma garantisce l'integrità del token e impedisce manomissioni. I dati da firmare
   * vengono codificati in Base64URL per garantire che possano essere trasmessi in modo sicuro.
   *
   * @param data i dati da firmare (header e payload).
   * @return la firma generata, codificata in Base64URL.
   */
  private String sign(String data) {
    try {
      Mac mac = Mac.getInstance(ALGORITHM);
      Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
      mac.init(secretKey);
      return Base64.getUrlEncoder()
          .withoutPadding()
          .encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      throw new RuntimeException(
          "Errore nella firma HMAC: " + e.getMessage(), e); // Gestisce errori nella firma
    }
  }
}
