package cz.restty.app.repositories;

import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Attribute;
import cz.restty.app.entities.Model;

/**
 * Repository that declares methods to work with {@link Attribute}s. Extends {@link CrudRepository} to provide basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface AttributeRepository extends CrudRepository<Attribute, Long> {

    /**
     * Removes all attributes of given model.
     * 
     * @param model
     *            {@link Model} to remove attributes by.
     */
    void removeAllByModel(Model model);

}

