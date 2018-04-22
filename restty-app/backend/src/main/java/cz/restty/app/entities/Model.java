package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.MODEL_SEQUENCE;
import static cz.restty.app.constants.DbConstants.MODEL_TABLE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity that contains information about models in project's endpoints.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = MODEL_TABLE)
@SequenceGenerator(name = MODEL_SEQUENCE, sequenceName = MODEL_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Model {

    private Long id;

    private String name;
    private Set<Attribute> attributes = new HashSet<>();

    private Project project;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = MODEL_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model", cascade = CascadeType.ALL)
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project", nullable = false, foreignKey = @ForeignKey(name = "id_project_model_fkey"))
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


}
