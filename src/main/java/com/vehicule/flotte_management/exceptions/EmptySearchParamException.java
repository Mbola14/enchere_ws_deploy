package com.vehicule.flotte_management.exceptions;

public class EmptySearchParamException extends Exception{
    public EmptySearchParamException(String strError) {
        super(strError);
    }
}
