package cz.restty.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cz.restty.app.entities.TestCase;
import cz.restty.app.entities.TestCaseSettings;

/**
 * Repository that declares methods to work with {@link TestCaseSettings}s. Extends {@link CrudRepository} that provides basic
 * functionality.
 * 
 * @author Ondrej Krpec
 *
 */
public interface TestCaseSettingsRepository extends CrudRepository<TestCaseSettings, Long> {

    /**
     * Finds all settings for given test case.
     * 
     * @param testCase
     *            {@link TestCase} to search by
     * @return list of test case settings
     */
    List<TestCaseSettings> findAllByParent(TestCase testCase);

    /**
     * Finds all settings for given test case
     * 
     * @param testCase
     *            TestCase to search by
     * @return list of test case settings
     */
    List<TestCaseSettings> findAllByTestCase(TestCase testCase);

    /**
     * Finds test case settings by parent and endpoint order
     * 
     * @param parent
     * @param endpointOrder
     * @return {@link TestCaseSettings}
     */
    TestCaseSettings findByParentAndEndpointOrder(TestCase parent, Integer endpointOrder);

}
