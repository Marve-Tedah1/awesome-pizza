package com.awesomepizza.repository;

import com.awesomepizza.model.OrdPizza;
import com.awesomepizza.model.Stato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdPizzaRepository extends JpaRepository<OrdPizza, Long> {
    OrdPizza findByStato(Stato stato);
    List<OrdPizza> findByStatoIn(List<Stato> stati);
}
