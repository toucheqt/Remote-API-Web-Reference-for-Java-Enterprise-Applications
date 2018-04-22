package cz.restty.app.rest.dto;

import org.springframework.http.HttpStatus;

/**
 * DTO that contains information about response.
 * 
 * @author Ondrej Krpec
 *
 */
public class ResponseDto {

    private HttpStatus httpStatus;

    private String modelName;
    private String description;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
