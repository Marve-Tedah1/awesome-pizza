package com.awesomepizza.controller;

import com.awesomepizza.model.Pizza;
import com.awesomepizza.service.PizzaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pizze")
public class PizzaController {
    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Pizza>> getPizze(){
        List<Pizza> pizze = pizzaService.getPizze();
        return ResponseEntity.ok().body(pizze);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Long id){
        Pizza pizza = pizzaService.findById(id);
        return ResponseEntity.ok(pizza);
    }

}
