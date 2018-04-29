package cz.restty.app.rest.dto;

/**
 * DTO that contains information about attributes.
 * 
 * @author Ondrej Krpec
 *
 */
public class AttributeDto extends IdNameDto {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
