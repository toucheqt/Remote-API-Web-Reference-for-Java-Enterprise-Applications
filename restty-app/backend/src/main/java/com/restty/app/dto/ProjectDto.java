package com.restty.app.dto;

import javax.validation.constraints.NotNull;

/**
 * DTO that contains information about project.
 * 
 * @author Ondrej Krpec
 *
 */
public class ProjectDto {

    @NotNull
    private String name;

    @NotNull
    private String source;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
