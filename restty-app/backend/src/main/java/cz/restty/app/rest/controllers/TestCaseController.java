package cz.restty.app.rest.controllers;

import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.controllers.validators.ProjectValidator;
import cz.restty.app.rest.controllers.validators.TestCaseValidator;
import cz.restty.app.rest.dto.TestCaseDto;
import cz.restty.app.service.TestCaseService;

/**
 * Controller that handles all /test-cases requests
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class TestCaseController {

    public static final String TEST_CASES_PATH = PROJECT_PATH + "/test-cases";
    public static final String TEST_CASE_PATH = TEST_CASES_PATH + "/{testCaseId}";
    public static final String TEST_CASE_VALIDATION_PATH = TEST_CASES_PATH + "/validate";

    public static final String RUN_TEST_CASE_PATH = TEST_CASE_PATH + "/run";

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private ProjectValidator projectValidator;
    
    @Autowired
    private TestCaseValidator testCaseValidator;

    /**
     * Finds all test cases for given project.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return list of test cases
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_CASES_PATH)
    public List<TestCaseDto> findAllByProject(@PathVariable Long projectId) {
        return testCaseRepository.findAllByProject(projectValidator.validate(projectId))
                .stream()
                .map(testCase -> new TestCaseDto(testCase))
                .collect(Collectors.toList());
    }

    /**
     * Finds test case by given ID.
     * 
     * @param testCaseId
     *            ID to search by
     * @return {@link TestCaseDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_CASE_PATH)
    public TestCaseDto findById(@PathVariable Long testCaseId) {
        return new TestCaseDto(testCaseValidator.validate(testCaseId));
    }

    /**
     * Finds test case with given name from given project.
     * 
     * @param projectId
     *            ID of project to search by
     * @param name
     *            Name to search by
     * @return {@link TestCase}
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_CASE_VALIDATION_PATH)
    public TestCase findByName(@PathVariable Long projectId, @RequestParam(name = "name", required = true) String name) {
        return testCaseRepository.findByProjectAndNameIgnoreCase(projectValidator.validate(projectId), name).orElse(null);
    }

    /**
     * Finds test case by given ID and runs it against the source server.
     * 
     * @param testCaseId
     *            ID of test case to run
     * @return {@link HttpStatus#OK} if run was successful, {@link HttpStatus#BAD_REQUEST} otherwise.
     */
    public ResponseEntity<?> run(@PathVariable Long testCaseId) {
        if (testCaseService.run(testCaseValidator.validate(testCaseId))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates test case from information in {@link TestCaseDto}.
     * 
     * @param projectId
     *            ID of project to add test case to
     * @param testCaseDto
     *            {@link TestCaseDto} that contains information about new test case
     * @return {@link TestCaseDto} and {@link HttpStatus#CREATED}
     */
    @PostMapping(TEST_CASES_PATH)
    public ResponseEntity<TestCaseDto> createTestCase(@PathVariable Long projectId, @RequestBody TestCaseDto testCaseDto) {
        Project project = projectValidator.validate(projectId);
        testCaseValidator.validateName(project, testCaseDto.getName());
        return new ResponseEntity<>(testCaseService.createTestCase(project, testCaseDto), HttpStatus.CREATED);
    }

    /**
     * Deletes test cases with given IDs that belong to the project.
     * 
     * @param projectId
     *            ID of project
     * @param testCasesIds
     *            IDs of test cases to delete
     * @return {@link HttpStatus#NO_CONTENT}
     */
    @DeleteMapping(TEST_CASES_PATH)
    public ResponseEntity<?> deleteTestCases(@PathVariable Long projectId, @RequestBody List<Long> testCasesIds) {
        if (CollectionUtils.isNotEmpty(testCasesIds)) {
            testCaseRepository.deleteAllByProjectAndIds(projectValidator.validate(projectId), testCasesIds);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

