package cz.restty.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;

/**
 * Repository that declares methods to work with {@link Header}s. Extends {@link CrudRepository} for basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface HeaderRepository extends CrudRepository<Header, Long> {
    
    /**
     * Finds all global headers for given {@link Project}.
     * 
     * @param project
     *            Project to search by
     * @return list of global headers
     */
    @Query("SELECT header FROM #{#entityName} header"
            + " INNER JOIN header.endpoints endpointHeader "
            + " INNER JOIN endpointHeader.endpoint endpoint "
            + " WHERE header.global = true AND endpoint.project = ?1"
            + " GROUP BY header ")
    List<Header> findAllGlobalByProject(Project project);
    
    /**
     * Finds all headers by given endpoint.
     * 
     * @param endpoint
     *            {@link Endpoint} to search by
     * @return list of headers
     */
    @Query("SELECT header FROM #{#entityName} header"
            + " INNER JOIN header.endpoints headerEndpoint"
            + " WHERE headerEndpoint.endpoint = ?1")
    List<Header> findAllByEndpoint(Endpoint endpoint);

    /**
     * Finds global header by header's name for given project.
     * 
     * @param project
     *            {@link Project} to search by
     * @param headerName
     *            Header's name to search by
     * @return global header with specified name
     */
    @Query("SELECT header FROM #{#entityName} header"
            + " INNER JOIN header.endpoints endpointHeader"
            + " INNER JOIN endpointHeader.endpoint endpoint"
            + " WHERE header.global = true AND endpoint.project = ?1 AND lower(header.header) = lower(?2) "
            + " GROUP BY header ")
    Optional<Header> findGlobalHeaderByName(Project project, String headerName);
    
    /**
     * Finds header by header's name and endpoint.
     * 
     * @param endpoint
     *            {@link Endpoint} to search by
     * @param headerName
     *            Header's name to search by
     * @return header with specified name
     */
    @Query("SELECT header FROM #{#entityName} header"
            + " INNER JOIN header.endpoints endpointHeader"
            + " WHERE header.global = false AND endpointHeader.endpoint = ?1 AND lower(header.header) = lower(?2)")
    Optional<Header> findHeaderByName(Endpoint endpoint, String headerName);

    /**
     * Finds global header by given id.
     * 
     * @param headerId
     *            ID to search by
     * @return global header or empty optional if such header does not exist
     */
    Optional<Header> findByGlobalTrueAndId(Long headerId);

    /**
     * Deletes headers with given IDs.
     * 
     * @param headerIds
     *            IDs of headers to delete
     */
    void deleteByIdIn(List<Long> headerIds);




    // /**
    // * Deletes all headers for given projects. Deletes both global and endpoint specific headers.
    // *
    // * @param project
    // * {@link Project} to delete headers for.
    // */
    // @Modifying
    // @Query("DELETE FROM #{#entityName} h WHERE h.endpoint IN (SELECT e FROM Endpoint e WHERE e.project = ?1)")
    // void deleteAllByProject(Project project);

}
