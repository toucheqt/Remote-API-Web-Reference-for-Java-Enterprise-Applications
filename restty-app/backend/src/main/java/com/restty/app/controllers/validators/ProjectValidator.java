package com.restty.app.controllers.validators;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restty.app.entities.Project;
import com.restty.app.exceptions.ResourceNotFoundException;
import com.restty.app.repositories.ProjectRepository;

/**
 * Validator for {@link Project} entities.
 * 
 * @author Ondrej Krpec
 *
 */
@Component
public class ProjectValidator {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Finds project by given ID.
     * 
     * @param projectId
     *            ID of project to search by
     * @return {@link Project}
     */
    public Project validate(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Project [ID=%d] does not exist.", projectId)));
    }

    /**
     * Validates that project's name does not already exist.
     * 
     * @param name
     *            project's name to validate
     * @param projectId
     *            ID of project to ignore when validating name (e.g. when renaming current project)
     */
    public void validateName(String name, Optional<Long> projectId) {
        if (StringUtils.isBlank(name) || name.length() < 2) {
            throw new IllegalArgumentException("Name must have at least two characters.");
        }

        if (!name.matches("[a-z0-9]([-a-z0-9]*[a-z0-9])?")) {
            throw new IllegalArgumentException(
                    "Project names may only contain lower-case letters, numbers and dashes. They may not start or end with a dash.");
        }

        Optional<Project> project = projectRepository.findByName(name);
        if ((project.isPresent() && !projectId.isPresent())
                || (project.isPresent() && projectId.isPresent() && !projectId.get().equals(project.get().getId()))) {
            throw new IllegalArgumentException(String.format("Project [name=%s] already exists.", name));
        }
    }
    
}
