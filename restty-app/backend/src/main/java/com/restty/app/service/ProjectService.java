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
     * @return {@link ProjectDto}
     */
    ProjectDto createProject(ProjectDto projectDto);

    /**
     * Renames project.
     * 
     * @param projectId
     *            ID of project to rename.
     * @param name
     *            New name of the project.
     * @return Updated project.
     */
    Project renameProject(Long projectId, String name);

    /**
     * Deletes project with given ID.
     * 
     * @param projectId
     *            ID of project to delete.
     */
    void deleteProject(Long projectId);

}
