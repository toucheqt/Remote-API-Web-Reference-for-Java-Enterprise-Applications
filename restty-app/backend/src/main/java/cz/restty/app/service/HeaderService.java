package cz.restty.app.service;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.HeaderDto;

/**
 * Service layer for manipulation with {@link Header} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface HeaderService {

    /**
     * Creates header for given project.
     * 
     * @param project
     *            {@link Project}
     * @param headerDto
     *            {@link HeaderDto}
     * @return {@link Header}
     */
    Header createGloabalHeader(Project project, HeaderDto headerDto);

    /**
     * Creates header for given endpoint.
     * 
     * @param endpoint
     *            {@link Endpoint}
     * @param headerDto
     *            {@link HeaderDto}
     * @return {@link Header}
     */
    Header createHeader(Endpoint endpoint, HeaderDto headerDto);

    /**
     * Updates global header's status (enabled / disabled) for given endpoint.
     * 
     * @param header
     *            {@link Header} to update
     * @param endpoint
     *            {@link Endpoint} to update
     */
    void updateGlobalHeaderStatus(Header header, Endpoint endpoint);

    /**
     * Updates given header.
     * 
     * @param header
     *            {@link Header} to rename.
     * @param headerDto
     *            DTO that contains new information about hear
     * @return updated header
     */
    Header updateHeader(Header header, HeaderDto headerDto);

}