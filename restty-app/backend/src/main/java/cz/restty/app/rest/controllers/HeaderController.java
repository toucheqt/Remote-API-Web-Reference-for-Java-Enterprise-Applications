package cz.restty.app.rest.controllers;

import static cz.restty.app.rest.controllers.ProjectController.PROJECT_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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

}
