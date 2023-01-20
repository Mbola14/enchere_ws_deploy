package com.vehicule.flotte_management.exceptions;

public class AuthenticationException extends Exception {
    public AuthenticationException(String error) {
        super(error);
    }
}
