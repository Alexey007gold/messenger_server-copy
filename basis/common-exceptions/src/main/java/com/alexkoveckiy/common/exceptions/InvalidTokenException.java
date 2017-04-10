package com.alexkoveckiy.common.exceptions;

/**
 * Created by alex on 15.03.17.
 */
public class InvalidTokenException extends Exception {
    private static final long serialVersionUID = -8607779252529469528L;

    public InvalidTokenException() {
    }

    public InvalidTokenException(String s) {
        super(s);
    }
}
