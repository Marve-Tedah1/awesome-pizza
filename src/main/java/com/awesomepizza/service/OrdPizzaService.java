package com.awesomepizza.service;

import com.awesomepizza.exception.OrderConflictException;
import com.awesomepizza.exception.ExceptionType;
import com.awesomepizza.exception.NotFoundException;
import com.awesomepizza.model.OrdPizza;
import com.awesomepizza.model.OrdResponses;
import com.awesomepizza.model.Pizza;
import com.awesomepizza.model.Stato;
import com.awesomepizza.repository.OrdPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.awesomepizza.model.Stato.*;

@Service
public class OrdPizzaService {
    private final OrdPizzaRepository ordPizzaRepository;
    private final PizzaService service;

    public OrdPizzaService(OrdPizzaRepository ordPizzaRepository, PizzaService service) {
        this.ordPizzaRepository = ordPizzaRepository;
        this.service = service;
    }

    public List<OrdResponses> getOrdini(){
        List<OrdResponses> responses = new ArrayList<>();
        try {
            List<OrdPizza> result = ordPizzaRepository.findAll();
            for (OrdPizza ordPizza: result){
                responses.add(new OrdResponses(ordPizza.getCod(), ordPizza.getStato().getDescrizione()));
            }
        } catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }

        return responses;
    }

    public OrdPizza nuovo(Long id){
        OrdPizza ord = new OrdPizza();
        try {
            Pizza pizza = service.findById(id);
            ord.setPizza(pizza);
            ord.setStato(ORDINATO);
            ordPizzaRepository.save(ord);
        } catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }

        return ord;
    }

    public List<OrdPizza> coda(){
        List<Stato> stati = Arrays.asList(ORDINATO, IN_PREPARAZIONE);
       return ordPizzaRepository.findByStatoIn(stati);
    }

    public OrdPizza aggiornaStato(Long id){
        Optional<OrdPizza> ordPizza = ordPizzaRepository.findById(id);
        if (ordPizza.isEmpty()){
            throw new NotFoundException("NON ESISTE PIZZA CON ID: "+id, ExceptionType.RISULTATO_DELLA_QUERY_NULL);
        }
        Stato statoOrd = ordPizza.get().getStato();
        switch (statoOrd) {
            case ORDINATO -> aggiornaStatoInPreparazione(ordPizza.get());
            case IN_PREPARAZIONE -> ordPizza.get().setStato(PRONTO);
            case PRONTO -> throw new OrderConflictException("L'ORDINE RISULTA GIA' PRONTA PER LA CONSEGNA!!", ExceptionType.STATO_NON_VALIDO);
        }
        return ordPizzaRepository.save(ordPizza.get());
    }

    private void aggiornaStatoInPreparazione(OrdPizza ordPizza) {
        OrdPizza byStato = ordPizzaRepository.findByStato(IN_PREPARAZIONE);
        if(byStato != null) {
            throw new OrderConflictException("L'ORDINE PRECEDENTE NON E' ANCORA IN STATO PRONTO");
        }
        ordPizza.setStato(IN_PREPARAZIONE);
    }
}
