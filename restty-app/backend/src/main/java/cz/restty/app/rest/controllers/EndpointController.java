package cz.restty.app.rest.controllers;

import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.rest.controllers.validators.EndpointValidator;
import cz.restty.app.rest.controllers.validators.ProjectValidator;
import cz.restty.app.rest.dto.EndpointDetailsDto;
import cz.restty.app.rest.dto.EndpointDto;
import cz.restty.app.rest.dto.StatsDto;

/**
 * Controller that handles all /endpoints requests
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class EndpointController {

    public static final String ENDPOINTS_PATH = PROJECT_PATH + "/endpoints";
    public static final String ENDPOINT_DETAIL_PATH = ENDPOINTS_PATH + "/{endpointId}";
    public static final String ENDPOINTS_STATS_PATH = ENDPOINTS_PATH + "/stats";

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private ProjectValidator projectValidator;
    
    @Autowired
    private EndpointValidator endpointValidator;

    /**
     * Finds all endpoints by given project ID.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return List of {@link EndpointDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(ENDPOINTS_PATH)
    public List<EndpointDto> findAllByProject(@PathVariable Long projectId) {
        return endpointRepository.findAllByProject(projectValidator.validate(projectId))
                .stream()
                .map(endpoint -> new EndpointDto(endpoint))
                .collect(Collectors.toList());
    }

    /**
     * Finds endpoint by given ID.
     * 
     * @param projectId
     *            ID of project to search by
     * @param endpointId
     *            ID of endpoint to search by
     * @return List of {@link EndpointDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(ENDPOINT_DETAIL_PATH)
    public EndpointDetailsDto findById(@PathVariable Long projectId, @PathVariable Long endpointId) {
        projectValidator.validate(projectId);
        return new EndpointDetailsDto(endpointValidator.validate(endpointId));
    }

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
        projectValidator.validate(projectId);
        return endpointRepository.getStatsByProject(projectId);
    }

}

