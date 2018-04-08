package cz.restty.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.HeaderDto;

/**
 * Repository that declares methods to work with {@link Header}s. Extends {@link CrudRepository} for basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface HeaderRepository extends CrudRepository<Header, Long> {

    /**
     * Finds all global headers for given {@link Project}.
     * 
     * @param project
     *            Project to search by.
     * @return list of {@link HeaderDto}
     */
    @Query("SELECT new cz.restty.app.rest.dto.HeaderDto(h.id, h.header, h.value)"
            + " FROM #{#entityName} h "
            + " WHERE h.project = ?1 AND h.endpoint IS NULL")
    List<HeaderDto> findAllGlobalByProject(Project project);

    /**
     * Deletes all headers for given projects. Deletes both global and endpoint specific headers.
     * 
     * @param project
     *            {@link Project} to delete headers for.
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE project = ?1")
    void deleteAllByProject(Project project);

}
