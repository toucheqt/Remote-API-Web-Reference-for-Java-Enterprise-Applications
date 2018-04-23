package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.ATTRIBUTE_SEQUENCE;
import static cz.restty.app.constants.DbConstants.ATTRIBUTE_TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity that contains information about attributes in models.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = ATTRIBUTE_TABLE)
@SequenceGenerator(name = ATTRIBUTE_SEQUENCE, sequenceName = ATTRIBUTE_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Attribute {

    private Long id;

    private String name;
    private String type;

    private Model model;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ATTRIBUTE_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_model", nullable = false, foreignKey = @ForeignKey(name = "id_model_attribute_fkey"))
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
