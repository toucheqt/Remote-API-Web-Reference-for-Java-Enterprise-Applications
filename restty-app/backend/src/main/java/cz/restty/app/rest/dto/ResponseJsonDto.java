package cz.restty.app.rest.dto;

import cz.restty.app.entities.Response;

/**
 * DTO that contains information about response's details.
 * 
 * @author Ondrej Krpec
 *
 */
public class ResponseJsonDto {

    private Long id;

    private String status;
    private String description;

    private ModelJsonDto model;

    public ResponseJsonDto(Response response) {
        this.id = response.getId();
        this.status = String.valueOf(response.getStatus().value());
        this.description = response.getDescription();
        if (response.getModel() != null) {
            this.model = new ModelJsonDto(response.getModel());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelJsonDto getModel() {
        return model;
    }

    public void setModel(ModelJsonDto model) {
        this.model = model;
    }

}
