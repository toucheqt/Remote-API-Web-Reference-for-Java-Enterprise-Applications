package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.HEADER_SEQUENCE;
import static cz.restty.app.constants.DbConstants.HEADER_TABLE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    private Boolean global = false;

    private Set<EndpointHeader> endpoints = new HashSet<>();

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
    @Column(name = "global", nullable = false, columnDefinition = "boolean default false")
    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "header", cascade = CascadeType.ALL)
    public Set<EndpointHeader> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Set<EndpointHeader> endpoints) {
        this.endpoints = endpoints;
    }

}
