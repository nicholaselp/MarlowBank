package com.elpidoroun.MarlowBank.exception;

public class ValidationException extends RuntimeException {

    private final String errorType;

    public ValidationException(String message){
        super(message);
        this.errorType = "Transaction error";
    }
}
