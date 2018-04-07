package com.restty.app.service;

import com.restty.app.entities.Endpoint;
import com.restty.app.entities.Project;
import com.restty.app.swagger.dto.Path;

/**
 * Service layer for manipulation with {@link Endpoint} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface EndpointService {

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

}