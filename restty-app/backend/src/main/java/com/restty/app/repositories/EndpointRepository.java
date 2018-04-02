package com.restty.app.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.restty.app.dto.StatsDto;
import com.restty.app.entities.Endpoint;

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
    @Query("SELECT new com.restty.app.dto.StatsDto("
            + " sum(CASE WHEN lastRunSuccess IS NULL THEN 1 END), "
            + " sum(CASE WHEN lastRunSuccess = true THEN 1 END), "
            + " sum(CASE WHEN lastRunSuccess = false THEN 1 END) "
            + ") FROM #{#entityName}")
    StatsDto getStatsByProject(Long projectId);
}