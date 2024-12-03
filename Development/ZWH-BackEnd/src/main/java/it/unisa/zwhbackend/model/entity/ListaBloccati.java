package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Classe che rappresenta la lista degli utenti bloccati.
 * <p>Questa classe mappa la tabella "lista_bloccati" nel database e tiene traccia degli utenti bloccati,
 * includendo l'ID dell'utente e la data di blocco.
 *
 * @author Giovanni Balzano
 */
@Entity
@Table(name = "lista_bloccati")
public class ListaBloccati {

    /**
     * Identificatore univoco della voce nella lista dei bloccati.
     * <p>La colonna è definita come chiave primaria {@code @Id} e il valore viene generato automaticamente
     * tramite {@code @GeneratedValue}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Data di blocco.
     * <p>Questo campo è obbligatorio e memorizza la data in cui l'utente è stato bloccato.
     * Usa {@code LocalDate} per rappresentare solo la data (senza orario).
     */
    @Column(nullable = false)
    private LocalDate dataBlocco;

    /**
     * Relazione molti-a-uno con l'entità Utente.
     * Un blocco è associato a un singolo utente.
     */
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;
}
