package cz.restty.app.rest.exceptions;

import cz.restty.app.rest.dto.RestErrorCode;

/**
 * Exception that occurs if the swagger api file is not available.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public class SwaggerFileUnavailableException extends CustomRuntimeException {

    public SwaggerFileUnavailableException(String msg) {
        super(msg, RestErrorCode.SWAGGER_FILE_UNAVAILABLE);
    }

}
