package cz.restty.app.rest.controllers;

import static cz.restty.app.constants.AppConstants.REST_API_PREFIX;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.repositories.LogRepository;
import cz.restty.app.rest.controllers.validators.EndpointValidator;
import cz.restty.app.rest.controllers.validators.TestCaseValidator;
import cz.restty.app.rest.dto.LogDto;

/**
 * Controller that handles all /logs requests.
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class LogController {

    public static final String ENDPOINT_LOGS_PATH = REST_API_PREFIX + "/endpoints/{endpointId}/logs";
    public static final String RECENT_ENDPOINT_LOG_PATH = ENDPOINT_LOGS_PATH + "/recent";

    public static final String TEST_CASE_LOGS_PATH = REST_API_PREFIX + "/test-cases/{testCaseId}/logs";
    public static final String RECENT_TEST_CASE_LOG_PATH = TEST_CASE_LOGS_PATH + "/recent";

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private EndpointValidator endpointValidator;

    @Autowired
    private TestCaseValidator testCaseValidator;

    /**
     * Finds all logs for given endpoint.
     * 
     * @param endpointId
     *            ID of endpoint to search by
     * @return list of logs
     */
    @Transactional(readOnly = true)
    @GetMapping(ENDPOINT_LOGS_PATH)
    public List<LogDto> findAllByEndpoint(@PathVariable Long endpointId) {
        return logRepository.findAllByEndpointOrderByRunDesc(endpointValidator.validate(endpointId))
                .stream()
                .map(log -> new LogDto(log))
                .collect(Collectors.toList());
    }

    /**
     * Finds all logs for given test case.
     * 
     * @param testCaseId
     *            ID of test case to search by
     * @return list of logs
     */
    @Transactional(readOnly = true)
    @GetMapping(TEST_CASE_LOGS_PATH)
    public List<LogDto> findAllByTestCase(@PathVariable Long testCaseId) {
        return logRepository.findAllByTestCaseOrderByRunDesc(testCaseValidator.validate(testCaseId))
                .stream()
                .map(log -> new LogDto(log))
                .collect(Collectors.toList());
    }

    /**
     * Finds most recent log for given endpoint.
     * 
     * @param endpointId
     *            ID of endpoint to search by
     * @return most recent log
     */
    @Transactional(readOnly = true)
    @GetMapping(RECENT_ENDPOINT_LOG_PATH)
    public LogDto findRecentByEndpoint(@PathVariable Long endpointId) {
        return new LogDto(logRepository.findTop1ByEndpointOrderByRunDesc(endpointValidator.validate(endpointId)));
    }

    /**
     * Finds most recent log for given test case.
     * 
     * @param testCaseId
     *            ID of test case to search by
     * @return most recent test case
     */
    @Transactional(readOnly = true)
    @GetMapping(RECENT_TEST_CASE_LOG_PATH)
    public LogDto findRecentByTestCase(@PathVariable Long testCaseId) {
        return new LogDto(logRepository.findTop1ByTestCaseOrderByRunDesc(testCaseValidator.validate(testCaseId)));
    }

}
