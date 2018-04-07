package cz.restty.app.service;

import java.io.IOException;

import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.ProjectDto;

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
     * @throws IOException
     *             If any problem while parsing swagger api file occurs
     */
    ProjectDto createProject(ProjectDto projectDto) throws IOException;

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
     * Deletes given project. Note that all correspondent API endpoints, test cases and settings are deleted too.
     * 
     * @param project
     *            {@link Project} to delete.
     */
    void deleteProject(Project project);

}
