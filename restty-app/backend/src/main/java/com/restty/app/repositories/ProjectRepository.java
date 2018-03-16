package com.restty.app.repositories;

import java.util.Optional;

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
     * @return {@link Project} or empty optional if project with provided name does not exist.
     */
    @Query("FROM #{#entityName} WHERE lower(name) = lower(?1)")
    Optional<Project> findByName(String name);

}
