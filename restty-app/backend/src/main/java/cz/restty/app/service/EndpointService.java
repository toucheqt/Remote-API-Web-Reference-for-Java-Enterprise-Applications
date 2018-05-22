package cz.restty.app.service;

import java.util.List;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Project;
import cz.restty.app.swagger.dto.Path;

/**
 * Service layer for manipulation with {@link Endpoint} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface EndpointService {

    /**
     * Runs given endpoint against the test server.
     * 
     * @param endpoint
     *            Endpoint to run.
     * @return True if run was successful, false otherwise.
     */
    boolean run(Endpoint endpoint);

    /**
     * Runs all endpoints for given project against the test server.
     * 
     * @param endpoints
     *            {@link Endpoint}
     * @return True if run was successful, false otherwise
     */
    boolean runAll(List<Endpoint> endpoints);

    /**
     * Creates endpoint for given project from given path.
     * 
     * @param project
     *            {@link Project}
     * @param path
     *            {@link Path}
     * @return {@link Endpoint}
     */
    Endpoint createEndpoint(Project project, Path path);

    /**
     * Deletes all endpoints for given project.
     * 
     * @param project
     *            {@link Project} to delete endpoints for.
     */
    void deleteAllByProject(Project project);

}