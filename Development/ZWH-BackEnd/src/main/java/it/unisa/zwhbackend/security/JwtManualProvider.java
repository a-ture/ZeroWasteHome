package it.unisa.zwhbackend.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

/**
 * Classe che implementa un provider manuale per la generazione di token JWT.
 *
 * <p>- Utilizza l'algoritmo HMAC-SHA512 per firmare i token. - Il token JWT è composto da header,
 * payload e firma, ciascuno codificato in Base64URL.
 *
 * <p>Metodi principali: - `generateToken(String email)`: genera un token JWT con l'indirizzo email
 * come soggetto. - `signHMACSHA512(String data, String secret)`: genera una firma HMAC-SHA512 per i
 * dati forniti utilizzando una chiave segreta.
 *
 * @author Marco Renella
 */
@Component
public class JwtManualProvider {

  private static final String SECRET_KEY = "secureKey123secureKey123secureKey123secureKey123";

  /**
   * Genera un token JWT per l'indirizzo email specificato.
   *
   * @param email l'indirizzo email dell'utente.
   * @return il token JWT generato.
   * @throws Exception in caso di errori nella generazione del token.
   */
  public String generateToken(String email) throws Exception {
    // Header
    String header = "{\"alg\":\"HS512\",\"typ\":\"JWT\"}";
    String encodedHeader =
        Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(header.getBytes(StandardCharsets.UTF_8));

    // Payload
    String payload =
        "{\"sub\":\"" + email + "\",\"iat\":" + (System.currentTimeMillis() / 1000) + "}";
    String encodedPayload =
        Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(payload.getBytes(StandardCharsets.UTF_8));

    // Signature
    String toSign = encodedHeader + "." + encodedPayload;
    String signature = signHMACSHA512(toSign, SECRET_KEY);

    // Combine all parts
    return encodedHeader + "." + encodedPayload + "." + signature;
  }

  /**
   * Firma i dati forniti utilizzando HMAC-SHA512 e una chiave segreta.
   *
   * @param data i dati da firmare.
   * @param secret la chiave segreta utilizzata per la firma.
   * @return la firma codificata in Base64URL.
   * @throws Exception in caso di errori nella generazione della firma.
   */
  private String signHMACSHA512(String data, String secret) throws Exception {
    Mac mac = Mac.getInstance("HmacSHA512");
    SecretKeySpec secretKeySpec =
        new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
    mac.init(secretKeySpec);
    byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
  }
}
