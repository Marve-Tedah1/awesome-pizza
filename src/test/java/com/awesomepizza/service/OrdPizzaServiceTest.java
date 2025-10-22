package com.awesomepizza.service;

import com.awesomepizza.exception.NotFoundException;
import com.awesomepizza.exception.OrderConflictException;
import com.awesomepizza.model.OrdPizza;
import com.awesomepizza.model.OrdResponses;
import com.awesomepizza.model.Pizza;
import com.awesomepizza.model.Stato;
import com.awesomepizza.repository.OrdPizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdPizzaServiceTest {
    @Mock
    private OrdPizzaRepository repository;
    private OrdPizzaService service;
    @Mock
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new OrdPizzaService(repository, pizzaService);
    }

    @Test
    void testGetOrdini() {
        List<OrdPizza> list = new ArrayList<>();
        Pizza pizza = new Pizza(1L,"MARGHERITA", 5.50, "pomodoro - mozzarella");
        OrdPizza ordPizza = new OrdPizza(1L, pizza, Stato.ORDINATO);
        list.add(ordPizza);
        when(repository.findAll()).thenReturn(list);
        List<OrdResponses> ordini = service.getOrdini();
        assertEquals(1, ordini.size());
        assertEquals(1, ordini.get(0).CodiceOrdine());
        assertEquals("Pizza Ordinata", ordini.get(0).StatoOrdine());
    }

    @Test
    void testGetOrdiniException() {
        when(repository.findAll()).thenThrow(new RuntimeException("ERRORE"));
        assertThrows(RuntimeException.class, ()->service.getOrdini());
    }

    @Test
    void testNuovo() {
        Pizza pizza = new Pizza(1L,"MARGHERITA", 5.50, "pomodoro - mozzarella");
        OrdPizza ordPizza = new OrdPizza(1L, pizza, Stato.ORDINATO);
        when(pizzaService.findById(anyLong())).thenReturn(pizza);
        when(repository.save(any())).thenReturn(ordPizza);
        OrdPizza nuovo = service.nuovo(pizza.getId());
        assertEquals(Stato.ORDINATO, nuovo.getStato());
        assertEquals(pizza, nuovo.getPizza());
        verify(repository).save(any(OrdPizza.class));
    }

    @Test
    void testNuovoException() {
        when(repository.findById(2L)).thenThrow(new NotFoundException("ERRORE"));
    }

    @Test
    void testCoda() {
        Pizza pizza = new Pizza(1L,"MARGHERITA", 5.50, "pomodoro - mozzarella");
        OrdPizza ordPizza = new OrdPizza(1L, pizza, Stato.ORDINATO);
        OrdPizza ordPizza2 = new OrdPizza(2L, pizza, Stato.IN_PREPARAZIONE);
        when(repository.findByStatoIn(List.of(Stato.ORDINATO, Stato.IN_PREPARAZIONE))).thenReturn(List.of(ordPizza, ordPizza2));
        assertEquals(2, service.coda().size());
    }

    @Test
    void testAggiornaStato() {
        Pizza pizza = new Pizza(1L,"MARGHERITA", 5.50, "pomodoro - mozzarella");
        OrdPizza ordPizza = new OrdPizza(1L, pizza, Stato.ORDINATO);
        when(repository.findById(ordPizza.getCod())).thenReturn(Optional.of(ordPizza));
        when(repository.findByStato(Stato.IN_PREPARAZIONE)).thenReturn(null);
        when(repository.save(any())).thenReturn(ordPizza);
        assertEquals(Stato.IN_PREPARAZIONE, service.aggiornaStato(ordPizza.getCod()).getStato());
        verify(repository).save(ordPizza);
    }

    @Test
    void testAggiornaStatoExceptionNotFound() {
        Long id = 11L;
        when(repository.findById(id)).thenThrow(new NotFoundException("NON ESISTE PIZZA"));
        assertThrows(NotFoundException.class, ()->service.aggiornaStato(id));
    }

    @Test
    void testAggiornaStatoExceptionBadRequest() {
        Pizza pizza = new Pizza(1L,"MARGHERITA", 5.50, "pomodoro - mozzarella");
        OrdPizza ordPizza = new OrdPizza(1L, pizza, Stato.IN_PREPARAZIONE);
        OrdPizza ordPizza2 = new OrdPizza(2L, new Pizza(), Stato.ORDINATO);
        when(repository.findById(ordPizza2.getCod())).thenReturn(Optional.of(ordPizza2));
        when(repository.findByStato(Stato.IN_PREPARAZIONE)).thenReturn(ordPizza);
        assertThrows(OrderConflictException.class, ()->service.aggiornaStato(ordPizza2.getCod()));
    }

    @Test
    void annullaOrdine() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new OrdPizza()));
        service.annullaOrdine(1L);
        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void annullaOrdineException(){
        when(repository.findById(anyLong())).thenThrow(new NotFoundException("ERRORE"));
        assertThrows(NotFoundException.class, ()->service.annullaOrdine(null));
    }
}