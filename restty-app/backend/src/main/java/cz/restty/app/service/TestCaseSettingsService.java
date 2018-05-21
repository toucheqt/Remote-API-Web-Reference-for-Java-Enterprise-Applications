package cz.restty.app.service;

import java.util.List;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.TestCase;
import cz.restty.app.entities.TestCaseSettings;
import cz.restty.app.rest.dto.IdNameMethodDto;

/**
 * Service layer for manipulation with {@link TestCaseSettings} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface TestCaseSettingsService {

    /**
     * Finds all steps for given test case.
     * 
     * @param testCase
     *            Test case to search by
     * @return list of step's ids, names and methods
     */
    List<IdNameMethodDto> findAllSteps(TestCase testCase);

    /**
     * Adds endpoint as a step of test case.
     * 
     * @param parent
     *            {@link TestCase}
     * @param step
     *            {@link Endpoint}
     * @param usePrevious
     *            connect the endpoint's input to previous step's output
     * @return {@link TestCaseSettings}
     */
    TestCaseSettings addEndpoint(TestCase parent, Endpoint step, Boolean usePrevious);

    /**
     * Adds test case as a step of test case.
     * 
     * @param parent
     *            {@link TestCase}
     * @param step
     *            {@link TestCase}
     * @return {@link TestCaseSettings}
     */
    TestCaseSettings addTestCase(TestCase parent, TestCase step);

    /**
     * Removes step with given order (and all following steps) from the test case.
     * 
     * @param testCase
     *            {@link TestCase}
     * @param stepOrder
     *            Step's order
     */
    void removeSteps(TestCase testCase, Integer stepOrder);

    /**
     * Removes all steps for given test case
     * 
     * @param testCase
     *            {@link TestCase}
     */
    void removeAllByTestCase(TestCase testCase);

}
