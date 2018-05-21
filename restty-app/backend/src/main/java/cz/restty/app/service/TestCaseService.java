package cz.restty.app.service;

import java.util.List;

import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.rest.dto.TestCaseDto;

/**
 * Service layer for manipulation with {@link TestCase} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface TestCaseService {

    /**
     * Runs given testCase against the test server.
     * 
     * @param testCase
     *            TestCase to run.
     * @return True if run was successful, false otherwise.
     */
    boolean run(TestCase testCase);


    /**
     * Creates {@link TestCase} in given project.
     * 
     * @param project
     *            {@link Project} to create test case for.
     * @param testCaseDto
     *            DTO that contains information about new test case.
     * @return new {@link TestCaseDto}
     */
    TestCaseDto createTestCase(Project project, TestCaseDto testCaseDto);

    /**
     * Deletes all test cases for given project.
     * 
     * @param project
     *            {@link Project} to delete test cases for.
     */
    void deleteAllByProject(Project project);

    /**
     * Deletes test cases with given IDs that belong to the project.
     * 
     * @param project
     *            {@link Project}
     * @param testCasesIds
     *            IDs of test cases to delete
     */
    void deleteTestCases(Project project, List<Long> testCasesIds);

}
