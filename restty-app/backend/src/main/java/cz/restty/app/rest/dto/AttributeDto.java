package cz.restty.app.rest.dto;

/**
 * DTO that contains information about attributes.
 * 
 * @author Ondrej Krpec
 *
 */
public class AttributeDto {

    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
