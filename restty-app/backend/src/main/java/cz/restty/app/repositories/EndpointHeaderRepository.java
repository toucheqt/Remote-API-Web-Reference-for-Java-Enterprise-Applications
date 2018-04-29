package cz.restty.app.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.EndpointHeader;
import cz.restty.app.entities.Header;

/**
 * Repository that declares methods to work with {@link EndpointHeader}s. Extends {@link CrudRepository} to provide basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface EndpointHeaderRepository extends CrudRepository<EndpointHeader, Long> {

    /**
     * Finds {@link EndpointHeader} by given endpoint and header.
     * 
     * @param endpoint
     *            {@link Endpoint} to search by
     * @param header
     *            {@link Header} to search by
     * @return {@link EndpointHeader} or empty optional if such endpoint's header does not exist
     */
    Optional<EndpointHeader> findByEndpointAndHeader(Endpoint endpoint, Header header);

}
