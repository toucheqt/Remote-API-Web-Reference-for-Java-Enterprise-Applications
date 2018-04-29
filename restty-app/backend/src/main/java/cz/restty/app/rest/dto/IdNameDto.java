package cz.restty.app.rest.dto;

/**
 * DTO that contains two properties - ID and name of the entity it represents.
 * 
 * @author Ondrej Krpec
 *
 */
public class IdNameDto {

    private Long id;
    private String name;

    public IdNameDto() {}

    public IdNameDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

}
