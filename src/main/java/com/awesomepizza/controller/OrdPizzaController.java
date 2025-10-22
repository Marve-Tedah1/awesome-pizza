package com.awesomepizza.controller;

import com.awesomepizza.model.OrdPizza;
import com.awesomepizza.model.OrdResponses;
import com.awesomepizza.service.OrdPizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordine")
public class OrdPizzaController {
    private final OrdPizzaService ordPizzaService;

    public OrdPizzaController(OrdPizzaService ordPizzaService) {
        this.ordPizzaService = ordPizzaService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<OrdResponses>> getOrdini(){
        List<OrdResponses> ordPizza = ordPizzaService.getOrdini();
        return ResponseEntity.ok(ordPizza);
    }

    @PostMapping("/nuovo/{idPizza}")
    public ResponseEntity<OrdPizza> creaOrdine(@PathVariable Long idPizza){
        OrdPizza ordPizza = ordPizzaService.nuovo(idPizza);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordPizza);
    }

    @DeleteMapping("/{codOrdine}/annulla")
    public ResponseEntity<Void> annullaOrdine(@PathVariable Long codOrdine){
        ordPizzaService.annullaOrdine(codOrdine);
        return ResponseEntity.noContent().build();
    }
}
