package cz.restty.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.StatsDto;

/**
 * Repository that declares methods to work with {@link Endpoint}s. Extends {@link CrudRepository} to provide basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface EndpointRepository extends CrudRepository<Endpoint, Long> {

    /**
     * Finds all {@link Endpoint} for given project.
     * 
     * @param project
     *            {@link Project} to search by.
     * @return list of endpoints
     */
    List<Endpoint> findAllByProject(Project project);

    /**
     * Finds endpoints statistics for given project.
     * 
     * @param projectId
     *            ID of project to search by
     * @return {@link StatsDto}
     */
    @Query("SELECT new cz.restty.app.rest.dto.StatsDto("
            + " sum(CASE WHEN l.run IS NULL THEN 1 END), "
            + " sum(CASE WHEN l.success = true THEN 1 END), "
            + " sum(CASE WHEN l.success = false THEN 1 END) "
            + ") FROM #{#entityName} e"
            + " LEFT JOIN e.logs l "
            + " WHERE e.project = ?1 "
            + " AND (l.id = (SELECT max(l2.id) FROM Log l2 WHERE l2.endpoint = e) OR l.id IS NULL)")
    StatsDto getStatsByProject(Project project);

}