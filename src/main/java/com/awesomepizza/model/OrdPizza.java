package com.awesomepizza.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrdPizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cod;
    @ManyToOne
    @JoinColumn(name = "pizza")
    private Pizza pizza;
    private Stato stato;
}
