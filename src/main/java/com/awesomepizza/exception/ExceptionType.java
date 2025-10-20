package com.awesomepizza.exception;

public enum ExceptionType {
    RISULTATO_DELLA_QUERY_NULL("NESSUN RISULTATO PER LA QUERY"),
    STATO_NON_VALIDO("LO STATO DELL'ORDINE NON E' VALIDO");
    private final String type;
    ExceptionType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
