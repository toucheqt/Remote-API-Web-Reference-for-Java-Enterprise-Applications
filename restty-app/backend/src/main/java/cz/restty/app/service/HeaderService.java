package cz.restty.app.service;

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
    Header createHeader(Project project, HeaderDto headerDto);

}