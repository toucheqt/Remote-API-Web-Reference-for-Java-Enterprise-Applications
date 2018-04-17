package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.HEADER_SEQUENCE;
import static cz.restty.app.constants.DbConstants.HEADER_TABLE;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity that contains information about headers.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = HEADER_TABLE)
@SequenceGenerator(name = HEADER_SEQUENCE, sequenceName = HEADER_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Header {

    private Long id;

    private String header;
    private String value;

    private Project project;
    private Endpoint endpoint;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = HEADER_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "header", nullable = false)
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @NotNull
    @Column(name = "value", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_project", nullable = false, foreignKey = @ForeignKey(name = "id_project_header_fkey"))
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endpoint", foreignKey = @ForeignKey(name = "id_endpoint_fkey"))
    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

}
