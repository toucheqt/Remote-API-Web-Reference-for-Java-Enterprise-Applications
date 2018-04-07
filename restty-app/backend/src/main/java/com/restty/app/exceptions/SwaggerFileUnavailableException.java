package com.restty.app.exceptions;

/**
 * Exception that occurs if the swagger api file is not available.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public class SwaggerFileUnavailableException extends RuntimeException {

    public SwaggerFileUnavailableException(String msg) {
        super(msg);
    }

}
