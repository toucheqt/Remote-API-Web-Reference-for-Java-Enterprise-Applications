package com.restty.app.entities;

/**
 * Entity that contains information about project.
 * 
 * @author Ondrej Krpec
 *
 */
public class Project {

    private Long id;

    private String name;
    private String source;

    private Integer endpoints = new Integer(0);
    private Integer tests = new Integer(0);

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Integer endpoints) {
        this.endpoints = endpoints;
    }

    public Integer getTests() {
        return tests;
    }

    public void setTests(Integer tests) {
        this.tests = tests;
    }

}
