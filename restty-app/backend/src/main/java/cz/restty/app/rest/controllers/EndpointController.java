package cz.restty.app.rest.controllers;

import static cz.restty.app.constants.AppConstants.REST_API_PREFIX;
import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.rest.controllers.validators.EndpointValidator;
import cz.restty.app.rest.controllers.validators.ProjectValidator;
import cz.restty.app.rest.dto.EndpointDetailsDto;
import cz.restty.app.service.EndpointService;

/**
 * Controller that handles all /endpoints requests.
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class EndpointController {

    public static final String ENDPOINTS_PATH = PROJECT_PATH + "/endpoints";
    public static final String ENDPOINT_PATH = REST_API_PREFIX + "/endpoints/{endpointId}";

    public static final String RUN_ALL_ENDPOINTS_PATH = "/api/endpoints/run";
    public static final String RUN_ENDPOINT_PATH = ENDPOINT_PATH + "/run";

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private ProjectValidator projectValidator;
    
    @Autowired
    private EndpointValidator endpointValidator;

    @Autowired
    private EndpointService endpointService;

    /**
     * Finds all endpoints by given project ID.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return List of {@link EndpointDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(ENDPOINTS_PATH)
    public List<EndpointDetailsDto> findAllByProject(@PathVariable Long projectId) {
        return endpointRepository.findAllByProject(projectValidator.validate(projectId))
                .stream()
                .map(endpoint -> new EndpointDetailsDto(endpoint))
                .collect(Collectors.toList());
    }

    /**
     * Finds endpoint by given ID.
     * 
     * @param endpointId
     *            ID of endpoint to search by
     * @return List of {@link EndpointDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(ENDPOINT_PATH)
    public EndpointDetailsDto findById(@PathVariable Long endpointId) {
        return new EndpointDetailsDto(endpointValidator.validate(endpointId));
    }

    /**
     * Finds endpoint by given ID and runs it against the source server.
     * 
     * @param endpointId
     *            ID of endpoint to run.
     * @return {@link HttpStatus#OK} if run was successful, {@link HttpStatus#BAD_REQUEST} otherwise.
     */
    @PostMapping(RUN_ENDPOINT_PATH)
    public ResponseEntity<?> run(@PathVariable Long endpointId) {
        if (endpointService.run(endpointValidator.validate(endpointId))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Finds all endpoints for given project and runs them against the source server
     * 
     * @param endpointIds
     *            IDs of endpoints to run
     * @return {@link HttpStatus#OK} if run was successful, {@link HttpStatus#BAD_REQUEST} otherwise.
     */
    @PostMapping(RUN_ALL_ENDPOINTS_PATH)
    public ResponseEntity<?> runAll(@RequestBody List<Long> endpointIds) {
        if (CollectionUtils.isNotEmpty(endpointIds)) {
            if (endpointService
                    .runAll(endpointIds.stream().map(endpointId -> endpointValidator.validate(endpointId)).collect(Collectors.toList()))) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

