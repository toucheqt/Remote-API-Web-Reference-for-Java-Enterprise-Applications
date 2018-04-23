package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.PARAMETER_SEQUENCE;
import static cz.restty.app.constants.DbConstants.PARAMETER_TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity that contains information about request's params.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = PARAMETER_TABLE)
@SequenceGenerator(name = PARAMETER_SEQUENCE, sequenceName = PARAMETER_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Parameter {

    private Long id;

    private ParamType type;
    private String name;

    private Boolean required;

    private String parameter;
    private String parameterValue;

    private Model model;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PARAMETER_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(name = "required", nullable = false)
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Column(name = "parameter")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Column(name = "parameter_value")
    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_model", foreignKey = @ForeignKey(name = "id_model_parameter_fkey"))
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
