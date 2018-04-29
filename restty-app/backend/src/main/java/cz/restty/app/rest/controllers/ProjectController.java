package cz.restty.app.rest.controllers;

import static cz.restty.app.constants.AppConstants.REST_API_PREFIX;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

import cz.restty.app.entities.Project;
import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.repositories.ProjectRepository;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.controllers.validators.ProjectValidator;
import cz.restty.app.rest.dto.ProjectDto;
import cz.restty.app.rest.dto.RunStatisticsDto;
import cz.restty.app.rest.dto.StatsDto;
import cz.restty.app.service.ProjectService;

/**
 * Controller that handles all /projects requests.
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class ProjectController {

    public static final String PROJECTS_PATH = REST_API_PREFIX + "/projects";
    public static final String PROJECT_VALIDATION_PATH = PROJECTS_PATH + "/validate";
    public static final String PROJECT_PATH = PROJECTS_PATH + "/{projectId}";

    public static final String PROJECT_STATISTICS_PATH = PROJECT_PATH + "/stats";
    public static final String ENDPOINTS_STATISTICS_PATH = PROJECT_STATISTICS_PATH + "/endpoints";
    public static final String TEST_CASE_STATISTICS_PATH = PROJECT_STATISTICS_PATH + "/test-cases";

    public static final String FAILED_RUNS_PATH = PROJECT_PATH + "/failed";
    public static final String RECENT_RUNS_PATH = PROJECT_PATH + "/recent";

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private ProjectValidator projectValidator;

    /**
     * Finds all projects.
     * 
     * @return list of {@link Project}s
     */
    @GetMapping(PROJECTS_PATH)
    @Transactional(readOnly = true)
    public List<ProjectDto> findProjects() {
        return projectRepository.findAllWithStats();
    }

    /**
     * Finds project by given name.
     * 
     * @param name
     *            Name to search project by.
     * @return {@link Project}
     */
    @GetMapping(PROJECT_VALIDATION_PATH)
    @Transactional(readOnly = true)
    public Project findByName(@RequestParam(name = "name", required = true) String name) {
        return projectRepository.findByNameIgnoreCase(name).orElse(null);
    }

    /**
     * Finds project by given id.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return {@link Project}
     */
    @GetMapping(PROJECT_PATH)
    @Transactional(readOnly = true)
    public Project findById(@PathVariable Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    /**
     * Finds five most recently called APIs or test cases.
     * 
     * @param projectId
     *            ID of project to search by
     * @return List of five {@link RunStatisticsDto}
     */
    @GetMapping(RECENT_RUNS_PATH)
    @Transactional(readOnly = true)
    public List<RunStatisticsDto> findRecentRuns(@PathVariable Long projectId) {
        return projectRepository.findRecentRuns(projectValidator.validate(projectId));
    }

    /**
     * Finds five most recently failed APIs or test cases calls.
     * 
     * @param projectId
     *            ID of project to search by
     * @return List of five {@link RunStatisticsDto}
     */
    @GetMapping(FAILED_RUNS_PATH)
    @Transactional(readOnly = true)
    public List<RunStatisticsDto> findRecentFailedRuns(@PathVariable Long projectId) {
        return projectRepository.findRecentFailedRuns(projectValidator.validate(projectId));
    }

    /**
     * Finds endpoints statistics for a given project.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return {@link StatsDto}
     */
    @GetMapping(ENDPOINTS_STATISTICS_PATH)
    @Transactional(readOnly = true)
    public StatsDto getEndpointStats(@PathVariable Long projectId) {
        return endpointRepository.getStatsByProject(projectValidator.validate(projectId));
    }

    /**
     * Finds test cases statistics for a given project.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return {@link StatsDto}
     */
    @GetMapping(TEST_CASE_STATISTICS_PATH)
    @Transactional(readOnly = true)
    public StatsDto getTestCasesStats(@PathVariable Long projectId) {
        return testCaseRepository.getStatsByProject(projectValidator.validate(projectId));
    }

    /**
     * Creates project from information in {@link ProjectDto}.
     * 
     * @param projectDto
     *            {@link ProjectDto} that contains information about new project.
     * @return {@link Project} and {@link HttpStatus#CREATED}
     * @throws IOException
     *             If any problem while parsing swagger API file occurs.
     */
    @PostMapping(PROJECTS_PATH)
    public ResponseEntity<ProjectDto> createProject(@RequestBody @Validated ProjectDto projectDto) throws IOException {
        projectValidator.validateName(projectDto.getName(), Optional.empty());
        return new ResponseEntity<>(new ProjectDto(projectService.createProject(projectDto)), HttpStatus.CREATED);
    }

    /**
     * Renames project.
     * 
     * @param projectId
     *            ID of project to rename.
     * @param projectDto
     *            DTO that contains new project name
     * @return Renamed project.
     */
    @PutMapping(PROJECT_PATH)
    public Project renameProject(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        projectValidator.validateName(projectDto.getName(), Optional.of(projectId));
        return projectService.renameProject(projectValidator.validate(projectId), projectDto.getName());
    }

    /**
     * Deletes project with given ID.
     * 
     * @param projectId
     *            ID of project to delete
     * @return {@link HttpStatus#NO_CONTENT}
     */
    @DeleteMapping(PROJECT_PATH)
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectValidator.validate(projectId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
