package cz.restty.app.rest.dto;

import cz.restty.app.entities.Parameter;

/**
 * DTO that contains information about parameters for json serialization
 * 
 * @author Ondrej Krpec
 *
 */
public class ParameterJsonDto {

    private Long id;

    private String type;
    private String name;

    private Boolean required;
    private String parameter;
    private String parameterValue;
    private ModelJsonDto model;

    public ParameterJsonDto() {}

    public ParameterJsonDto(Parameter parameter) {
        this.id = parameter.getId();
        this.type = parameter.getType().getName();
        this.name = parameter.getName();
        this.required = parameter.getRequired();
        this.parameter = parameter.getParameter();
        this.parameterValue = parameter.getParameterValue();
        if (parameter.getModel() != null) {
            this.model = new ModelJsonDto(parameter.getModel());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public ModelJsonDto getModel() {
        return model;
    }

    public void setModel(ModelJsonDto model) {
        this.model = model;
    }

}
