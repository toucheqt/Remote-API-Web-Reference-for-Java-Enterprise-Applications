package cz.restty.app.rest.controllers;

import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.controllers.validators.ProjectValidator;
import cz.restty.app.rest.dto.StatsDto;

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
    private TestCaseRepository testCaseRepository;

    @Autowired
    private ProjectValidator projectValidator;

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

}

