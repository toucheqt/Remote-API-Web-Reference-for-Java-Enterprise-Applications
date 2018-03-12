package com.restty.app.entities;

import static com.restty.app.constants.DbConstants.PROJECT_SEQUENCE;
import static com.restty.app.constants.DbConstants.PROJECT_TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity that contains information about project.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = PROJECT_TABLE)
@SequenceGenerator(name = PROJECT_SEQUENCE, sequenceName = PROJECT_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Project {

    private Long id;

    private String name;
    private String source;

    private Integer endpoints = new Integer(0);
    private Integer tests = new Integer(0);

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PROJECT_SEQUENCE)
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

    @NotNull
    @Column(name = "source", nullable = false)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @NotNull
    @Column(name = "endpoints", nullable = false, columnDefinition = "integer default 0")
    public Integer getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Integer endpoints) {
        this.endpoints = endpoints;
    }

    @NotNull
    @Column(name = "tests", nullable = false, columnDefinition = "integer default 0")
    public Integer getTests() {
        return tests;
    }

    public void setTests(Integer tests) {
        this.tests = tests;
    }

}
