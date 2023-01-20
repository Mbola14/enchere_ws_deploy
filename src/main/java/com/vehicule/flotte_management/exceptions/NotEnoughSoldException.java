package com.vehicule.flotte_management.exceptions;

public class NotEnoughSoldException extends Exception {
    public NotEnoughSoldException(String strError) {
        super(strError);
    }
}
