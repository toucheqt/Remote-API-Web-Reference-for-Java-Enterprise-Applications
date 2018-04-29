package cz.restty.app.service;

import java.io.IOException;

import org.springframework.web.client.RestClientException;

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
     * @return {@link Project}
     * @throws RestClientException
     *             If any problem while retrieving the Swagger's API file occurs.
     * @throws IOException
     *             If any problem while parsing Swagger's API file occurs.
     */
    Project createProject(ProjectDto projectDto) throws RestClientException, IOException;

    /**
     * Renames project.
     * 
     * @param project
     *            Project to rename.
     * @param name
     *            New name of the project.
     * @return Updated project.
     */
    Project renameProject(Project project, String name);


    /*
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */





    /**
     * Deletes given project. Note that all correspondent API endpoints, test cases and settings are deleted too.
     * 
     * @param project
     *            {@link Project} to delete.
     */
    void deleteProject(Project project);

}
