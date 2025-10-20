package com.awesomepizza.exception;

public class OrderBadRequestException extends RuntimeException{
    private ExceptionType type;
    public OrderBadRequestException(String msg, ExceptionType type){
        super(msg);
        this.type = type;
    }
    public OrderBadRequestException(String msg) {
        super(msg);
    }
}
