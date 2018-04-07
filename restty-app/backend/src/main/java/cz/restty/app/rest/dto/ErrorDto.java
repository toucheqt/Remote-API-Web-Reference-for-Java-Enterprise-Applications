package cz.restty.app.rest.dto;

import java.util.Map;

/**
 * Represents information about application error.
 * 
 * @author Ondrej Krpec
 *
 */
public class ErrorDto {

    private String errorCode;
    private Map<String, Object> params;

    public ErrorDto(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorDto(String errorCode, Map<String, Object> params) {
        this.errorCode = errorCode;
        this.params = params;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
