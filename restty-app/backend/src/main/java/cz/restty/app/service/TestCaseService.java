package cz.restty.app.service;

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
     * Creates {@link TestCase} in given project.
     * 
     * @param project
     *            {@link Project} to create test case for.
     * @param testCaseDto
     *            DTO that contains information about new test case.
     * @return new {@link TestCaseDto}
     */
    TestCaseDto createTestCase(Project project, TestCaseDto testCaseDto);

}
