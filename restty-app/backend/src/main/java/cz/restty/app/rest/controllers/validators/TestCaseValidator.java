package cz.restty.app.rest.controllers.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.dto.RestErrorCode;
import cz.restty.app.rest.exceptions.ResourceNotFoundException;
import cz.restty.app.rest.exceptions.ValidationException;

/**
 * Validator for {@link TestCase} entities.
 * 
 * @author Ondrej Krpec
 *
 */
@Component
public class TestCaseValidator {

    @Autowired
    private TestCaseRepository testCaseRepository;

    /**
     * Finds and validates that test case with given ID exists.
     * 
     * @param testCaseId
     *            ID of test case to search by
     * @return {@link TestCase}
     */
    public TestCase validate(Long testCaseId) {
        return testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Test case [ID=%d] does not exist.", testCaseId),
                        RestErrorCode.TEST_CASE_NOT_FOUND));
    }

    /**
     * Validates that test case with given name does not already exist in the project.
     * 
     * @param project
     *            {@link Project} to search by
     * @param name
     *            test case's name to validate
     */
    public void validateName(Project project, String name) {
        if (StringUtils.isBlank(name) || name.length() <= 2) {
            throw new ValidationException("Name must have at least two characters.", RestErrorCode.TEST_CASE_NAME_INVALID);
        }

        testCaseRepository.findByProjectAndNameIgnoreCase(project, name).ifPresent(testCase -> {
            throw new ValidationException(String.format("Test case [name=%s] already exist.", name), RestErrorCode.TEST_CASE_NAME_INVALID);
        });
    }

}
