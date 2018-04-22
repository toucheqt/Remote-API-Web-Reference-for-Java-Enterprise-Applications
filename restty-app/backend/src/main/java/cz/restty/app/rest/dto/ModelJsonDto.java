package cz.restty.app.rest.dto;

import java.util.List;
import java.util.stream.Collectors;

import cz.restty.app.entities.Attribute;
import cz.restty.app.entities.Model;

/**
 * DTO that contains information about models for json serialization
 * 
 * @author Ondrej Krpec
 *
 */
public class ModelJsonDto {

    private Long id;

    private String name;
    private List<Attribute> attributes;

    public ModelJsonDto(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.attributes = model.getAttributes().stream().collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

}
