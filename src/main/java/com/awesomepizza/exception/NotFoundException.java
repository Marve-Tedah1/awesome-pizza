package com.awesomepizza.exception;

public class NotFoundException extends RuntimeException {
    private ExceptionType type;
    public NotFoundException(String msg, ExceptionType type){
        super(msg);
        this.type = type;
    }
    public NotFoundException(String msg){
        super(msg);
    }
}
