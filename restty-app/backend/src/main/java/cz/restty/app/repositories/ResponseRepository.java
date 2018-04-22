package cz.restty.app.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Model;
import cz.restty.app.entities.Response;

/**
 * Repository that declares method to work with {@link Response}s. Extends {@link CrudRepository} to provide basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ResponseRepository extends CrudRepository<Response, Long> {

    /**
     * Deletes all responses with given model
     * 
     * @param model
     *            {@link Model} to delete responses with.
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE model = ?1")
    void deleteAllByModel(Model model);

}
