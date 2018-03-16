package com.restty.app.controllers.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restty.app.entities.Project;
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
     * Validates that project's name does not already exist.
     * 
     * @param name
     *            project's name to validate
     */
    public void validateName(String name) {
        if (StringUtils.isBlank(name) || name.length() <= 2) {
            throw new IllegalArgumentException("Name must have at least two characters.");
        }

        if (!name.matches("[a-z0-9]([-a-z0-9]*[a-z0-9])?")) {
            throw new IllegalArgumentException(
                    "Project names may only contain lower-case letters, numbers and dashes. They may not start or end with a dash.");
        }

        projectRepository.findByName(name)
                .ifPresent(project -> new IllegalArgumentException(String.format("Project [name=%s] already exists.", name)));
    }
    
}
