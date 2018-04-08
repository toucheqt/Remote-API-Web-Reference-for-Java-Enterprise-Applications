package cz.restty.app.rest.dto;

import javax.validation.constraints.NotNull;

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

    public HeaderDto() {}

    public HeaderDto(Header header) {
        this.id = header.getId();
        this.header = header.getHeader();
        this.value = header.getValue();
    }

    // constructor is used by reflection in HeaderRepository
    public HeaderDto(Long id, String header, String value) {
        this.id = id;
        this.header = header;
        this.value = value;
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

}
