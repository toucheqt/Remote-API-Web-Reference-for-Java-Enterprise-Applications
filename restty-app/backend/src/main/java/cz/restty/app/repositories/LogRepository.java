package cz.restty.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Log;
import cz.restty.app.entities.TestCase;

/**
 * Repository that declares methods to work with {@link Log}s. Extends {@link CrudRepository} for basic functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface LogRepository extends CrudRepository<Log, Long> {

    /**
     * Finds all logs for given endpoint.
     * 
     * @param endpoint
     *            {@link Endpoint} to search by
     * @return list of logs
     */
    List<Log> findAllByEndpointOrderByRunDesc(Endpoint endpoint);

    /**
     * Finds all logs for given test case.
     * 
     * @param testCase
     *            {@link TestCase} to search by
     * @return list of logs
     */
    List<Log> findAllByTestCaseOrderByRunDesc(TestCase testCase);

    /**
     * Finds most recent log for given endpoint.
     * 
     * @param endpoint
     *            {@link Endpoint} to search by
     * @return most recent log
     */
    Log findTop1ByEndpointOrderByRunDesc(Endpoint endpoint);

    /**
     * Finds most recent log for given test case.
     * 
     * @param testCase
     *            {@link TestCase} to search by
     * @return most recent log
     */
    Log findTop1ByTestCaseOrderByRunDesc(TestCase testCase);


    /**
     * Deletes all logs for given endpoint
     * 
     * @param endpoint
     *            {@link Endpoint} to delete logs for
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE endpoint = ?1")
    void deleteAllByEndpoint(Endpoint endpoint);

    /**
     * Deletes all logs for given test case
     * 
     * @param endpoint
     *            {@link TestCase} to delete logs for
     */
    @Modifying
    @Query("DELETE FROM #{#entityName} WHERE testCase = ?1")
    void deleteAllByTestCase(TestCase testCase);

}
