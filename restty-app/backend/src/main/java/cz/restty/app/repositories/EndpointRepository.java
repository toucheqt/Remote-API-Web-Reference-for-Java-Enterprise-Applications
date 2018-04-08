package cz.restty.app.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.StatsDto;

/**
 * Repository that declares method to work with {@link Endpoint}s. Extends {@link CrudRepository} to provide basic functionality.
 * @author Ondrej Krpec
 *
 */
public interface EndpointRepository extends CrudRepository<Endpoint, Long> {

    /**
     * Finds endpoints statistics for given project.
     * 
     * @param projectId
     *            ID of project to search by
     * @return {@link StatsDto}
     */
    @Query("SELECT new cz.restty.app.rest.dto.StatsDto("
            + " sum(CASE WHEN e.lastRunSuccess IS NULL THEN 1 END), "
            + " sum(CASE WHEN e.lastRunSuccess = true THEN 1 END), "
            + " sum(CASE WHEN e.lastRunSuccess = false THEN 1 END) "
            + ") FROM #{#entityName} e "
            + " WHERE e.project.id = ?1")
    StatsDto getStatsByProject(Long projectId);

    /**
     * Deletes all endpoints for given project.
     * 
     * @param project
     *            {@link Project} to delete endpoints for.
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE project = ?1")
    void deleteAllByProject(Project project);
}