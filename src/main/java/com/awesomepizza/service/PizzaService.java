package com.awesomepizza.service;

import com.awesomepizza.exception.ExceptionType;
import com.awesomepizza.exception.NotFoundException;
import com.awesomepizza.model.Pizza;
import com.awesomepizza.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> getPizze(){
        List<Pizza> result = pizzaRepository.findAll();
        return result;
    }

    public Pizza findById(Long id){
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isEmpty()){
            throw new NotFoundException("NON ESISTE PIZZA CON ID: "+id, ExceptionType.RISULTATO_DELLA_QUERY_NULL);
        }
        return result.get();
    }
}
