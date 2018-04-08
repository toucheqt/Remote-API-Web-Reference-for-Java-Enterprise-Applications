package cz.restty.app.rest.controllers;

import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import java.util.List;
import java.util.Optional;

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

import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;
import cz.restty.app.repositories.HeaderRepository;
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

    public static final String HEADERS_PATH = PROJECT_PATH + "/headers";
    public static final String HEADER_NAME_PATH = HEADERS_PATH + "/{headerName}";

    public static final String GLOBAL_HEADERS_PATH = PROJECT_PATH + "/global-headers";

    @Autowired
    private HeaderService headerService;

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private ProjectValidator projectValidator;

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
        return headerRepository.findAllGlobalByProject(projectValidator.validate(projectId));
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
    @GetMapping(HEADER_NAME_PATH)
    public HeaderDto findByHeaderName(@PathVariable Long projectId, @PathVariable String headerName) {
        Optional<Header> header = headerRepository.findByHeaderName(projectId, headerName);
        if (header.isPresent()) {
            return new HeaderDto(header.get());
        }

        return null;
    }

    /**
     * Creates header for given project.
     * 
     * @param project
     *            {@link Project}
     * @param headerDto
     *            {@link HeaderDto}
     * @return {@link Header} and {@link HttpStatus#CREATED}
     */
    @PostMapping(HEADERS_PATH)
    public ResponseEntity<HeaderDto> addHeader(@PathVariable Long projectId, @Validated @RequestBody HeaderDto headerDto) {
        return new ResponseEntity<>(new HeaderDto(headerService.createHeader(projectValidator.validate(projectId), headerDto)),
                HttpStatus.CREATED);
    }

    /**
     * Deletes headers with given ids from given project.
     * 
     * @param projectId
     *            ID of project
     * @param headerIds
     *            IDs of headers to delete
     * @return {@link HttpStatus#NO_CONTENT}
     */
    @DeleteMapping(HEADERS_PATH)
    public ResponseEntity<?> deleteHeaders(@PathVariable Long projectId, @RequestBody List<Long> headerIds) {
        if (CollectionUtils.isNotEmpty(headerIds)) {
            headerRepository.deleteByIds(projectId, headerIds);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
