package com.awesomepizza.service;

import com.awesomepizza.exception.NotFoundException;
import com.awesomepizza.model.Pizza;
import com.awesomepizza.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaServiceTest {
    @Mock
    private PizzaRepository repository;
    private PizzaService service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new PizzaService(repository);
    }

    @Test
    void testGetPizze() {
        List<Pizza> list = new ArrayList<>();
        Pizza pizza = new Pizza(1L,"MARGHERITA", 5.50, "pomodoro - mozzarella");
        list.add(pizza);
        when(repository.findAll()).thenReturn(list);
        List<Pizza> result = service.getPizze();
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    void testFindById() {
        Pizza pizza = new Pizza(1L,"MARGHERITA", 5.50, "pomodoro - mozzarella");
        when(repository.findById(anyLong())).thenReturn(Optional.of(pizza));
        when(repository.findById(2L)).thenThrow(NotFoundException.class);
        Pizza pizza1 = service.findById(pizza.getId());
        assertEquals(1, pizza1.getId());
    }
}