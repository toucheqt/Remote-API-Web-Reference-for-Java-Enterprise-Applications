package cz.restty.app.rest.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO that contains information about models.
 * 
 * @author Ondrej Krpec
 *
 */
public class ModelDto {

    private Long id;

    private String name;

    private Set<AttributeDto> attributes = new HashSet<>();

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

    public Set<AttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeDto> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(AttributeDto attribute) {
        attributes.add(attribute);
    }

}
