package cz.restty.app.rest.dto;

import org.apache.commons.collections4.CollectionUtils;

import cz.restty.app.entities.Project;

/**
 * DTO that contains information about project.
 * 
 * @author Ondrej Krpec
 *
 */
public class ProjectDto extends IdNameDto {

    private String source;

    private Long tests;
    private Long endpoints;

    public ProjectDto() {}

    public ProjectDto(Project project) {
        super(project.getId(), project.getName());
        this.source = project.getSource();

        if (CollectionUtils.isNotEmpty(project.getEndpoints())) {
            this.endpoints = project.getEndpoints().stream().count();
        }

        if (CollectionUtils.isNotEmpty(project.getTestCases())) {
            this.tests = project.getTestCases().stream().count();
        }
    }

    // constructor is used by reflection in ProjectRepository
    public ProjectDto(Long id, String name, String source, Long endpoints, Long tests) {
        super(id, name);
        this.source = source;
        this.tests = tests;
        this.endpoints = endpoints;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getTests() {
        return tests;
    }

    public void setTests(Long tests) {
        this.tests = tests;
    }

    public Long getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Long endpoints) {
        this.endpoints = endpoints;
    }

}
