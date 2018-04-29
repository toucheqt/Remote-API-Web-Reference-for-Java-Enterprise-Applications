package cz.restty.app.rest.dto;

import javax.validation.constraints.NotNull;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Header;

/**
 * DTO that contains information about headers.
 * 
 * @author Ondrej Krpec
 *
 */
public class HeaderDto {

    private Long id;

    @NotNull
    private String header;

    @NotNull
    private String value;

    private Boolean global;
    private Boolean enabled;

    public HeaderDto() {}

    public HeaderDto(Header header) {
        this.id = header.getId();
        this.header = header.getHeader();
        this.value = header.getValue();
        this.global = header.getGlobal();
    }

    public HeaderDto(Header header, Endpoint endpoint) {
        this(header);

        header.getEndpoints().forEach(eh1 -> {
            if (endpoint.getHeaders().contains(eh1)) {
                this.enabled = eh1.getEnabled();
            }
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
