package cz.restty.app.rest.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO that contains information about models.
 * 
 * @author Ondrej Krpec
 *
 */
public class ModelDto extends IdNameDto {

    private Set<AttributeDto> attributes = new HashSet<>();

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
