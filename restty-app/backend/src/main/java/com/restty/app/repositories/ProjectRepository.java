package com.restty.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.restty.app.dto.ProjectDto;
import com.restty.app.entities.Project;

/**
 * Repository that declares methods to work with {@link Project}s. Extends {@link CrudRepository} for basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {

    /**
     * Finds project by the given name.
     * 
     * @param name
     *            Name to search by
     * @return {@link Project} or empty optional if project with provided name does not exist.
     */
    @Query("FROM #{#entityName} WHERE lower(name) = lower(?1) ORDER BY name ASC")
    Optional<Project> findByName(String name);

    /**
     * Finds all projects with information about amount of endpoints and test cases.
     * 
     * @return list of {@link ProjectDto}
     */
    @Query("SELECT new com.restty.app.dto.ProjectDto("
            + "  p.id, p.name, p.source, count(e.id), 0" 
            + " ) " 
            + " FROM #{#entityName} p "
            + " LEFT JOIN p.endpoints e "
            + " GROUP BY p.id, p.name, p.source ")
    List<ProjectDto> findAllWithStats();

}
