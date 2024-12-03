package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.SegnalazioneRicetta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione delle segnalazioni delle ricette. {@link SegnalazioneRicetta}.
 *
 * <p>Fornisce metodi CRUD predefiniti tramite l'estensione di {@link JpaRepository}.
 *
 * @author Giovanni Balzano
 */
public interface SegnalazioneRicettaRepository extends JpaRepository<SegnalazioneRicetta, Long> {}
