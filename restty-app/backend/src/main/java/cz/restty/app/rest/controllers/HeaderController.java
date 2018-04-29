package cz.restty.app.rest.controllers;

import static cz.restty.app.constants.AppConstants.REST_API_PREFIX;
import static cz.restty.app.rest.controllers.EndpointController.ENDPOINT_PATH;
import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;
import cz.restty.app.repositories.HeaderRepository;
import cz.restty.app.rest.controllers.validators.EndpointValidator;
import cz.restty.app.rest.controllers.validators.HeaderValidator;
import cz.restty.app.rest.controllers.validators.ProjectValidator;
import cz.restty.app.rest.dto.HeaderDto;
import cz.restty.app.service.HeaderService;

/**
 * Controller that handles all /headers and /global-headers requests.
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class HeaderController {

    public static final String GLOBAL_HEADERS_PATH = PROJECT_PATH + "/global-headers";
    public static final String GLOBAL_HEADER_VALIDATION_PATH = GLOBAL_HEADERS_PATH + "/validate";

    public static final String HEADERS_PATH = REST_API_PREFIX + "/headers";
    public static final String HEADER_PATH = HEADERS_PATH + "/{headerId}";

    public static final String HEADERS_ENDPOINT_PATH = ENDPOINT_PATH + "/headers";
    public static final String HEADERS_ENDPOINT_VALIDATION_PATH = HEADERS_ENDPOINT_PATH + "/validate";
    public static final String HEADER_ENDPOINT_PATH = HEADERS_ENDPOINT_PATH + "/{headerId}";

    @Autowired
    private HeaderService headerService;

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private HeaderValidator headerValidator;

    @Autowired
    private EndpointValidator endpointValidator;

    /**
     * Finds all global headers for given {@link Project}.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return List of {@link HeaderDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(GLOBAL_HEADERS_PATH)
    public List<HeaderDto> findAllGlobalHeaders(@PathVariable Long projectId) {
        return headerRepository.findAllGlobalByProject(projectValidator.validate(projectId))
                .stream()
                .map(header -> new HeaderDto(header))
                .collect(Collectors.toList());
    }

    /**
     * Finds {@link Header} for given project by given name.
     * 
     * @param projectId
     *            ID of project to search by
     * @param headerName
     *            Header name to search by
     * @return {@link HeaderDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(GLOBAL_HEADER_VALIDATION_PATH)
    public HeaderDto findGlobalByHeaderName(@PathVariable Long projectId, @RequestParam(name = "header", required = true) String headerName) {
        Optional<Header> header = headerRepository.findGlobalHeaderByName(projectValidator.validate(projectId), headerName);
        if (header.isPresent()) {
            return new HeaderDto(header.get());
        }

        return null;
    }

    /**
     * Finds {@link Header} by given endpoint and header name.
     * 
     * @param endpointId
     *            ID of endpoint to search by
     * @param headerName
     *            Header's name to search by
     * @return {@link HeaderDto}
     */
    @Transactional(readOnly = true)
    @GetMapping(HEADERS_ENDPOINT_VALIDATION_PATH)
    public HeaderDto findByHeaderName(@PathVariable Long endpointId, @RequestParam(name = "header", required = true) String headerName) {
        Optional<Header> header = headerRepository.findHeaderByName(endpointValidator.validate(endpointId), headerName);
        if (header.isPresent()) {
            return new HeaderDto(header.get());
        }

        return null;
    }

    /**
     * Finds all headers by given endpoint.
     * 
     * @param endpointId
     *            ID of endpoint to search by
     * @return list of headers
     */
    @Transactional(readOnly = true)
    @GetMapping(HEADERS_ENDPOINT_PATH)
    public List<HeaderDto> findAllByEndpoint(@PathVariable Long endpointId) {
        Endpoint endpoint = endpointValidator.validate(endpointId);
        return headerRepository.findAllByEndpoint(endpoint)
                .stream()
                .map(header -> new HeaderDto(header, endpoint))
                .collect(Collectors.toList());
    }

    /**
     * Creates header for given project.
     * 
     * @param projectId
     *            ID of project to create header for
     * @param headerDto
     *            {@link HeaderDto}
     * @return {@link HeaderDto} and {@link HttpStatus#CREATED}
     */
    @PostMapping(GLOBAL_HEADERS_PATH)
    public ResponseEntity<HeaderDto> createGlobalHeader(@PathVariable Long projectId, @Validated @RequestBody HeaderDto headerDto) {
        return new ResponseEntity<>(new HeaderDto(headerService.createGloabalHeader(projectValidator.validate(projectId), headerDto)),
                HttpStatus.CREATED);
    }

    /**
     * Creates header for given endpoint.
     * 
     * @param endpointId
     *            ID of endpoint to create header for
     * @param headerDto
     *            {@link HeaderDto}
     * @return {@link HeaderDto} and {@link HttpStatus#CREATED}
     */
    @PostMapping(HEADERS_ENDPOINT_PATH)
    public ResponseEntity<HeaderDto> createHeader(@PathVariable Long endpointId, @Validated @RequestBody HeaderDto headerDto) {
        return new ResponseEntity<>(new HeaderDto(headerService.createHeader(endpointValidator.validate(endpointId), headerDto)),
                HttpStatus.CREATED);
    }

    /**
     * Updates global header's status (enabled / disabled) for given endpoint.
     * 
     * @param endpointId
     *            ID of endpoint to update by
     * @param headerId
     *            ID of header to update by
     */
    @PutMapping(HEADER_ENDPOINT_PATH)
    public void updateGlobalHeaderStatus(@PathVariable Long endpointId, @PathVariable Long headerId) {
        headerService.updateGlobalHeaderStatus(headerValidator.validateGlobal(headerId), endpointValidator.validate(endpointId));
    }

    /**
     * Updates given header.
     * 
     * @param headerId
     *            ID of header to rename
     * @param headerDto
     *            DTO that contains new header name and value
     * @return updated header
     */
    @PutMapping(HEADER_PATH)
    public HeaderDto updateHeader(@PathVariable Long headerId, @RequestBody HeaderDto headerDto) {
        return new HeaderDto(headerService.updateHeader(headerValidator.validate(headerId), headerDto));
    }

    /**
     * Deletes headers with given IDs.
     * 
     * @param headerIds
     *            IDs of headers to delete.
     * @return {@link HttpStatus#NO_CONTENT}
     */
    @DeleteMapping(HEADERS_PATH)
    public ResponseEntity<?> deleteHeaders(@RequestBody List<Long> headerIds) {
        if (CollectionUtils.isNotEmpty(headerIds)) {
            headerRepository.deleteByIdIn(headerIds);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
