package it.unisa.zwhbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale per l'applicazione backend di Zero Waste Home.
 *
 * <p>Questa classe avvia l'applicazione Spring Boot. Annota la classe con
 * {@code @SpringBootApplication}, che include le seguenti annotazioni:
 *
 * <ul>
 *   <li>{@code @Configuration}: per definire la classe come sorgente di configurazione.
 *   <li>{@code @EnableAutoConfiguration}: per abilitare la configurazione automatica di Spring
 *       Boot.
 *   <li>{@code @ComponentScan}: per consentire la scansione dei componenti all'interno del
 *       pacchetto.
 * </ul>
 *
 * Per avviare l'applicazione, utilizza il metodo {@code SpringApplication.run}.
 *
 * @author Alessia Ture
 */
@SpringBootApplication
public class ZwhBackEndApplication {

  /**
   * Metodo principale per l'avvio dell'applicazione.
   *
   * <p>Questo metodo utilizza {@code SpringApplication.run} per avviare il contesto Spring,
   * configurare i bean e inizializzare l'applicazione.
   *
   * @param args array di stringhe che rappresenta gli argomenti della riga di comando.
   */
  public static void main(String[] args) {
    SpringApplication.run(ZwhBackEndApplication.class, args);
  }
}
