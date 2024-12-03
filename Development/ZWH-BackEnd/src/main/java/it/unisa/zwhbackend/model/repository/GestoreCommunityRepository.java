package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.GestoreCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dell'accesso ai dati dell'entità {@link GestoreCommunity}.
 *
 * <p>Fornisce metodi CRUD predefiniti tramite l'estensione di {@link JpaRepository}.
 *
 * @author Giovanni Balzano
 */
public interface GestoreCommunityRepository extends JpaRepository<GestoreCommunity, Long> { }
