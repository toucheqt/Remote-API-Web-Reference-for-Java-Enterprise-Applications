package com.restty.app.service;

import com.restty.app.dto.ProjectDto;
import com.restty.app.entities.Project;

/**
 * Service layer for manipulation with {@link Project} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ProjectService {

    /**
     * Creates project from {@link ProjectDto} information.
     * 
     * @param projectDto
     *            {@link ProjectDto}
     * @return {@link Project}
     */
    Project createProject(ProjectDto projectDto);

}
