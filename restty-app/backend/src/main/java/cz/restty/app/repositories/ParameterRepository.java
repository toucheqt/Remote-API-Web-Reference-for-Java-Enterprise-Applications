package cz.restty.app.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Model;
import cz.restty.app.entities.Parameter;

/**
 * Repository that declares method to work with {@link Parameter}s. Extends {@link CrudRepository} to provide basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ParameterRepository extends CrudRepository<Parameter, Long> {

    /**
     * Deletes all parameters with given model
     * 
     * @param model
     *            {@link Model} to delete parameters with.
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE model = ?1")
    void deleteAllByModel(Model model);

}
