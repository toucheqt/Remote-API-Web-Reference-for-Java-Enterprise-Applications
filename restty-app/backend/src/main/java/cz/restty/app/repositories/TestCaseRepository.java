package cz.restty.app.repositories;

import java.util.List;
import java.util.Optional;

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
     * Finds all test cases by given project.
     * 
     * @param project
     *            {@link Project} to search by.
     * @return List of test cases that belong to given project.
     */
    List<TestCase> findAllByProject(Project project);

    /**
     * Finds test case from given project with given name.
     * 
     * @param project
     *            {@link Project} to search by.
     * @param name
     *            Name to search by.
     * @return {@link TestCase} or empty optional if such test case does not exist.
     */
    Optional<TestCase> findByProjectAndNameIgnoreCase(Project project, String name);

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

    /**
     * Deletes test cases with given IDs that belong to the project.
     * 
     * @param projectId
     *            ID of project
     * @param testCasesIds
     *            IDs of test cases to delete
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE project = ?1 AND id IN (?2)")
    void deleteAllByProjectAndIds(Project project, List<Long> testCasesIds);

}
