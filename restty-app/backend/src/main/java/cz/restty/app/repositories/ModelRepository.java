package cz.restty.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Model;
import cz.restty.app.entities.Project;

/**
 * Repository that declares method to work with {@link Model}s. Extends {@link CrudRepository} to provide basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ModelRepository extends CrudRepository<Model, Long> {

    /**
     * Finds {@link Model} by given name for given project. Method ignores case.
     * 
     * @param project
     *            Project to search by.
     * @param name
     *            Name to search by.
     * @return {@link Model} or empty optional if such model does not exist.
     */
    Optional<Model> findByProjectAndNameIgnoreCase(Project project, String name);

    /**
     * Finds all models for given project.
     * 
     * @param project
     *            Project to search by
     * @return List of models
     */
    List<Model> findAllByProject(Project project);

}
