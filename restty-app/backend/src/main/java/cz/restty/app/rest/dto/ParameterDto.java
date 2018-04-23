package cz.restty.app.rest.dto;

/**
 * DTO that contains information about request's parameters.
 * 
 * @author Ondrej Krpec
 *
 */
public class ParameterDto {

    private String in;
    private String name;
    private boolean required = false;

    private String modelName;
    private String parameter;

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
