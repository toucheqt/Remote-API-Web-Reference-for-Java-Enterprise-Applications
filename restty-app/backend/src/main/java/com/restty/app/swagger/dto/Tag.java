package com.restty.app.swagger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO that contains information about tags.
 * 
 * @author Ondrej Krpec
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
