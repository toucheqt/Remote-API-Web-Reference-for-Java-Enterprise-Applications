package cz.restty.app.rest.controllers;

import static cz.restty.app.constants.AppConstants.REST_API_PREFIX;
import static cz.restty.app.rest.controllers.TestCaseController.TEST_CASE_PATH;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.TestCase;
import cz.restty.app.repositories.TestCaseSettingsRepository;
import cz.restty.app.rest.controllers.validators.EndpointValidator;
import cz.restty.app.rest.controllers.validators.TestCaseValidator;
import cz.restty.app.rest.dto.IdNameMethodDto;
import cz.restty.app.rest.dto.TestCaseSettingsDto;
import cz.restty.app.service.TestCaseSettingsService;

/**
 * Controller that handles all /test-cases/{testCaseId}/settings requests
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class TestCaseSettingsController {

    public static final String TEST_CASE_SETTINGS_PATH = TEST_CASE_PATH + "/settings";
    public static final String TEST_STEPS_PATH = TEST_CASE_SETTINGS_PATH + "/steps";
    public static final String SETTINGS_PATH = REST_API_PREFIX + "/test-cases/settings/{settingsId}";

    public static final String ADD_ENDPOINT_STEP = TEST_CASE_SETTINGS_PATH + "/endpoints/{endpointId}";
    public static final String ADD_TEST_STEP = TEST_CASE_SETTINGS_PATH + "/test-cases/{testStepId}";

    @Autowired
    private TestCaseSettingsService testCaseSettingsService;

    @Autowired
    private TestCaseSettingsRepository testCaseSettingsRepository;

    @Autowired
    private TestCaseValidator testCaseValidator;

    @Autowired
    private EndpointValidator endpointValidator;

    /**
     * Finds all test case settings by given test case ID.
     * 
     * @param testCaseId
     *            ID of test case to search by
     * @return List of {@link TestCaseSettingsDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_CASE_SETTINGS_PATH)
    public List<TestCaseSettingsDto> findAllByTestCaseId(@PathVariable Long testCaseId) {
        return testCaseSettingsRepository.findAllByParent(testCaseValidator.validate(testCaseId))
                .stream()
                .map(settings -> new TestCaseSettingsDto(settings))
                .collect(Collectors.toList());
    }

    /**
     * Finds all steps for given test case.
     * 
     * @param testCase
     *            Test case to search by
     * @return list of step's ids, names and methods
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_STEPS_PATH)
    public List<IdNameMethodDto> findAllSteps(@PathVariable Long testCaseId) {
        return testCaseSettingsService.findAllSteps(testCaseValidator.validate(testCaseId));
    }

    /**
     * Finds settings by given id
     * 
     * @param settingsId
     *            ID to search by
     * @return {@link TestCaseSettingsDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(SETTINGS_PATH)
    public TestCaseSettingsDto findById(@PathVariable Long settingsId) {
        return new TestCaseSettingsDto(testCaseSettingsRepository.findById(settingsId).get());
    }

    /**
     * Adds endpoint as a step of test case.
     * 
     * @param testCaseId
     *            ID of test case
     * @param endpointId
     *            ID of step (endpoint)
     * @param usePrevious
     *            connect the endpoint's input to previous step's output
     * @return {@link TestCaseSettingsDto}
     */
    @PostMapping(ADD_ENDPOINT_STEP)
    public TestCaseSettingsDto addEndpointStep(@PathVariable Long testCaseId, @PathVariable Long endpointId,
            @RequestBody(required = false) Boolean usePrevious) {
        TestCase parent = testCaseValidator.validate(testCaseId);
        Endpoint step = endpointValidator.validate(endpointId);

        return new TestCaseSettingsDto(testCaseSettingsService.addEndpoint(parent, step, usePrevious));
    }

    /**
     * Adds test case as a step of test case.
     * 
     * @param testCaseId
     *            ID of test case
     * @param testStepId
     *            ID of step (test case)
     * @return {@link TestCaseSettingsDto}
     */
    @PostMapping(ADD_TEST_STEP)
    public TestCaseSettingsDto addTestStep(@PathVariable Long testCaseId, @PathVariable Long testStepId) {
        TestCase parent = testCaseValidator.validate(testCaseId);
        TestCase step = testCaseValidator.validate(testStepId);

        return new TestCaseSettingsDto(testCaseSettingsService.addTestCase(parent, step));
    }

    /**
     * Removes step with given order (and all following steps) from the test case.
     * 
     * @param testCaseId
     *            ID of {@link TestCase}
     * @param stepOrder
     *            Step's order
     * @return {@link HttpStatus#NO_CONTENT}
     */
    @DeleteMapping(TEST_CASE_SETTINGS_PATH)
    public ResponseEntity<?> deleteSteps(@PathVariable Long testCaseId, @RequestBody Integer stepOrder) {
        testCaseSettingsService.removeSteps(testCaseValidator.validate(testCaseId), stepOrder);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
