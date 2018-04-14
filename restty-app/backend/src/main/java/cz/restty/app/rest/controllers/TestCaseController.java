package cz.restty.app.rest.controllers;

import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.controllers.validators.ProjectValidator;
import cz.restty.app.rest.controllers.validators.TestCaseValidator;
import cz.restty.app.rest.dto.StatsDto;
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
    public static final String TEST_CASES_STATS_PATH = TEST_CASES_PATH + "/stats";

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
    public List<TestCaseDto> findAllByProjectId(@PathVariable Long projectId) {
        return testCaseRepository.findAllByProject(projectValidator.validate(projectId))
                .stream()
                .map(testCase -> new TestCaseDto(testCase))
                .collect(Collectors.toList());
    }

    /**
     * Finds test case from given project with given name.
     * 
     * @param projectId
     *            ID of project to search by.
     * @param name
     *            Name to search by.
     * @return {@link TestCase}
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_CASES_PATH + "/validate/{name}")
    public TestCase findByName(@PathVariable Long projectId, @PathVariable String name) {
        return testCaseRepository.findByProjectAndNameIgnoreCase(projectValidator.validate(projectId), name).orElse(null);
    }

    /**
     * Finds test case statistics for given project
     * 
     * @param projectId
     *            ID of project to search by
     * @return {@link StatsDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_CASES_STATS_PATH)
    public StatsDto getStatsByProject(@PathVariable Long projectId) {
        projectValidator.validate(projectId);
        return testCaseRepository.getStatsByProject(projectId);
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

    /**
     * Creates test case from information in {@link TestCaseDto}
     * 
     * @param projectId
     *            ID of project to add test case to
     * @param testCaseDto
     *            {@link TestCaseDto} that contains information about new test case
     * @return {@link TestCaseDto} and {@link HttpStatus#CREATED}
     */
    @PostMapping(TEST_CASES_PATH)
    public ResponseEntity<TestCaseDto> createTestCase(@PathVariable Long projectId, @RequestBody @Validated TestCaseDto testCaseDto) {
        Project project = projectValidator.validate(projectId);
        testCaseValidator.validateName(project, testCaseDto.getName());
        return new ResponseEntity<>(testCaseService.createTestCase(project, testCaseDto), HttpStatus.CREATED);
    }

}

