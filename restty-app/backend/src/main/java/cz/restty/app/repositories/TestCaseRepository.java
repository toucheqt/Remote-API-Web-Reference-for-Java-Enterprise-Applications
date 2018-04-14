package cz.restty.app.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.rest.dto.StatsDto;

/**
 * Repository that declares methods to work with {@link TestCase}s. Extends {@link CrudRepository} that provides basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface TestCaseRepository extends CrudRepository<TestCase, Long> {

    /**
     * Finds test case statistics for given project.
     * 
     * @param projectId
     *            ID of project to search by
     * @return {@link StatsDto}
     */
    @Query("SELECT new cz.restty.app.rest.dto.StatsDto("
            + " sum(CASE WHEN tc.lastRunSuccess IS NULL THEN 1 END), "
            + " sum(CASE WHEN tc.lastRunSuccess = true THEN 1 END), "
            + " sum(CASE WHEN tc.lastRunSuccess = false THEN 1 END) "
            + ") FROM #{#entityName} tc "
            + " WHERE tc.project.id = ?1")
    StatsDto getStatsByProject(Long projectId);

    /**
     * Deletes all test cases for given project.
     * 
     * @param project
     *            {@link Project} to delete test cases for.
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE project = ?1")
    void deleteAllByProject(Project project);

}

