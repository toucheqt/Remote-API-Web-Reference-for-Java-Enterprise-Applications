package cz.restty.app.rest.exceptions;

import cz.restty.app.rest.dto.RestErrorCode;

/**
 * Base class for custom runtime exceptions. Contains error code which is propagated to client via REST service response.
 * 
 * @author Ondrej Krpec
 *
 */
@SuppressWarnings("serial")
public abstract class CustomRuntimeException extends RuntimeException {

    private RestErrorCode errorCode;

    public CustomRuntimeException(String message, RestErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RestErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(RestErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
