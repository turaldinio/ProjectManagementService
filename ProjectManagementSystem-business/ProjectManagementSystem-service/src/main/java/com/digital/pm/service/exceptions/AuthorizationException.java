package com.digital.pm.service.exceptions;

public class AuthorizationException extends RuntimeException{

    public AuthorizationException(String message){
        super(message);
    }
}
