package com.restty.app.exceptions;

/**
 * Exception that occurs when the requested resource does not exist.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}