package com.awesomepizza.exception;

public class OrderConflictException extends RuntimeException{
    private ExceptionType type;
    public OrderConflictException(String msg, ExceptionType type){
        super(msg);
        this.type = type;
    }
    public OrderConflictException(String msg) {
        super(msg);
    }
}
