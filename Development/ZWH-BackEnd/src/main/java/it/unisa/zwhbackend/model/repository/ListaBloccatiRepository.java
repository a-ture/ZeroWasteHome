package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.ListaBloccati;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaBloccatiRepository extends JpaRepository<ListaBloccati, Long> {
}
