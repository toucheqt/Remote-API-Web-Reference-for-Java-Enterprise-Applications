package cz.restty.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.HeaderDto;

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
     *            Project to search by.
     * @return list of {@link HeaderDto}
     */
    @Query("SELECT new cz.restty.app.rest.dto.HeaderDto(h.id, h.header, h.value)"
            + " FROM #{#entityName} h "
            + " WHERE h.project = ?1 AND h.endpoint IS NULL")
    List<HeaderDto> findAllGlobalByProject(Project project);

    /**
     * Finds {@link Header} for given project by given name.
     * 
     * @param projectId
     *            ID of project to search by
     * @param headerName
     *            Header name to search by
     * @return {@link Header}
     */
    @Query("FROM #{#entityName} WHERE project.id = ?1 AND lower(header) = lower(?2)")
    Optional<Header> findByHeaderName(Long projectId, String headerName);

    /**
     * Deletes all headers for given projects. Deletes both global and endpoint specific headers.
     * 
     * @param project
     *            {@link Project} to delete headers for.
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE project = ?1")
    void deleteAllByProject(Project project);

    /**
     * Deletes headers with given ids from given project.
     * 
     * @param projectId
     *            ID of project
     * @param headerIds
     *            IDs of headers to delete
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE project.id = ?1 AND id IN (?2)")
    void deleteByIds(Long projectId, List<Long> headerIds);

}
