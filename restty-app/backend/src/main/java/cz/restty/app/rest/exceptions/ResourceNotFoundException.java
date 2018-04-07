package cz.restty.app.rest.exceptions;

import cz.restty.app.rest.dto.RestErrorCode;

/**
 * Exception that occurs when the requested resource does not exist.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends CustomRuntimeException {

    public ResourceNotFoundException(String msg, RestErrorCode errorCode) {
        super(msg, errorCode);
    }

}