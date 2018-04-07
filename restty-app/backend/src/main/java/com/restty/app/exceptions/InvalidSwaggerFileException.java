package com.restty.app.exceptions;

/**
 * Exception that occurs given swagger json api file is not valid.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public class InvalidSwaggerFileException extends RuntimeException {

    public InvalidSwaggerFileException(String msg) {
        super(msg);
    }

}