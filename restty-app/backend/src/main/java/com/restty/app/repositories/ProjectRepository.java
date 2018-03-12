package com.restty.app.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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
     * @return {@link Project} or null if project with provided name does not exist.
     */
    @Query("FROM #{entityName} WHERE lower(name) = lower(?1)")
    Project getByName(String name);

}
