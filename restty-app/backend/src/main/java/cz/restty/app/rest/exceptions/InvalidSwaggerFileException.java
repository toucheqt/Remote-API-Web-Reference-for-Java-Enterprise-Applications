package cz.restty.app.rest.exceptions;

import cz.restty.app.rest.dto.RestErrorCode;

/**
 * Exception that occurs given swagger json api file is not valid.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public class InvalidSwaggerFileException extends CustomRuntimeException {

    public InvalidSwaggerFileException(String message) {
        super(message, RestErrorCode.SWAGGER_FILE_INVALID);
    }

}