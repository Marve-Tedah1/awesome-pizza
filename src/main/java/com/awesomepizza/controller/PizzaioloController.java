package com.awesomepizza.controller;

import com.awesomepizza.model.OrdPizza;
import com.awesomepizza.service.OrdPizzaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PizzaioloController {
    private final OrdPizzaService service;

    public PizzaioloController(OrdPizzaService service) {
        this.service = service;
    }

    @GetMapping("/ordine/coda")
    public ResponseEntity<List<OrdPizza>> coda(){
        List<OrdPizza> codaOrdine = service.coda();
        return ResponseEntity.ok(codaOrdine);
    }

    @PutMapping("/ordine/{id}/aggiorna")
    public ResponseEntity<OrdPizza> aggiornaStato(@PathVariable Long id){
        OrdPizza ordPizza = service.aggiornaStato(id);
        return ResponseEntity.ok(ordPizza);
    }
}
