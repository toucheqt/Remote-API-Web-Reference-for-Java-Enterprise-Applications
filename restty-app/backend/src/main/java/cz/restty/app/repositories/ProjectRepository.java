package cz.restty.app.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Project;
import cz.restty.app.repositories.custom.ProjectRepositoryCustom;

/**
 * Repository that declares methods to work with {@link Project}s. Extends {@link CrudRepository} for basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ProjectRepository extends CrudRepository<Project, Long>, ProjectRepositoryCustom {

    /**
     * Finds project by the given name.
     * 
     * @param name
     *            Name to search by
     * @return {@link Project} or empty optional if project with provided name does not exist.
     */
    Optional<Project> findByNameIgnoreCase(String name);

}
