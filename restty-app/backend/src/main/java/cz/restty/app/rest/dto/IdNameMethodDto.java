package cz.restty.app.rest.dto;

import org.springframework.http.HttpMethod;

/**
 * DTO that contains information about item's ID, name and method.
 * 
 * @author Ondrej Krpec
 *
 */
public class IdNameMethodDto extends IdNameDto {

    private String method;

    public IdNameMethodDto(Long id, String name, HttpMethod method) {
        super(id, name);
        if (method != null) {
            this.method = method.toString();
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
