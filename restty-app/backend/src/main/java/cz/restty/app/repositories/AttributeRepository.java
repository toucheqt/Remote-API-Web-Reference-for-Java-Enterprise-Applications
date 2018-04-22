package cz.restty.app.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Attribute;
import cz.restty.app.entities.Model;

/**
 * Repository that declares method to work with {@link Attribute}s. Extends {@link CrudRepository} to provide basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface AttributeRepository extends CrudRepository<Attribute, Long> {

    /**
     * Deletes all attributes with given model
     * 
     * @param model
     *            {@link Model} to delete attributes with.
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE model = ?1")
    void deleteAllByModel(Model model);

}

