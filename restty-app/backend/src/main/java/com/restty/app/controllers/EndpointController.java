package com.restty.app.controllers;

import static com.restty.app.controllers.ProjectController.PROJECT_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.restty.app.dto.StatsDto;
import com.restty.app.repositories.EndpointRepository;

/**
 * Controller that handles all /api requests
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class EndpointController {

    public static final String ENDPOINTS_PATH = PROJECT_PATH + "/endpoints";
    public static final String ENDPOINTS_STATS_PATH = ENDPOINTS_PATH + "/stats";

    @Autowired
    private EndpointRepository endpointRepository;

    /**
     * Finds endpoints statistics for given project.
     * 
     * @param projectId
     *            ID of project to search by
     * @return {@link StatsDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(ENDPOINTS_STATS_PATH)
    public StatsDto getStatsByProject(@PathVariable Long projectId) {
        return endpointRepository.getStatsByProject(projectId);
    }

}

