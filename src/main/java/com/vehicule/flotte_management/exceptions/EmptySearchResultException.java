package com.vehicule.flotte_management.exceptions;

public class EmptySearchResultException extends Exception {
    public EmptySearchResultException(String strError) {
        super(strError);
    }
}
