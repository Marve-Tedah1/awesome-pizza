package com.awesomepizza.model;

public enum Stato {
    ORDINATO("Pizza Ordinata"),
    IN_PREPARAZIONE("In preparazione "),
    PRONTO("Pizza Pronta");

    private final String descrizione;
    Stato(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione(){
        return descrizione;
    }
}
