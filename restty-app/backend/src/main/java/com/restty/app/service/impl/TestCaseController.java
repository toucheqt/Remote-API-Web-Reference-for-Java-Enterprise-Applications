package com.restty.app.service.impl;

import static com.restty.app.controllers.ProjectController.PROJECT_PATH;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.restty.app.dto.StatsDto;

/**
 * Controller that handles all /test-case requests
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class TestCaseController {

    public static final String TEST_CASES_PATH = PROJECT_PATH + "/test-cases";
    public static final String TEST_CASES_STATS_PATH = TEST_CASES_PATH + "/stats";

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
        return new StatsDto(0, 0, 0);
    }

}

