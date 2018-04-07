package cz.restty.app.rest.exceptions;

import cz.restty.app.rest.dto.RestErrorCode;

/**
 * Exception that occurs when item is not valid.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public class ValidationException extends CustomRuntimeException {

    public ValidationException(String message, RestErrorCode errorCode) {
        super(message, errorCode);
    }

}
