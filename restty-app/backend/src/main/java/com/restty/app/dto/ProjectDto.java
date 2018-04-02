package com.restty.app.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;

import com.restty.app.entities.Project;

/**
 * DTO that contains information about project.
 * 
 * @author Ondrej Krpec
 *
 */
public class ProjectDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String source;

    private long tests;
    private long endpoints;

    public ProjectDto() {}

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.source = project.getSource();
        if (CollectionUtils.isNotEmpty(project.getEndpoints())) {
            this.endpoints = project.getEndpoints().stream().count();
        }

        this.tests = 0; // TODO
    }

    // constructor is used by reflection in ProjectRepository
    public ProjectDto(Long id, String name, String source, long endpoints, int tests) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.tests = tests;
        this.endpoints = endpoints;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getTests() {
        return tests;
    }

    public void setTests(long tests) {
        this.tests = tests;
    }

    public long getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(long endpoints) {
        this.endpoints = endpoints;
    }

}
