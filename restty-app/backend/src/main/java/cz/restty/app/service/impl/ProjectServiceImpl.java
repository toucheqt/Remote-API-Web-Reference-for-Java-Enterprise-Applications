package cz.restty.app.service.impl;

import static cz.restty.app.rest.dto.RestErrorCode.PROJECT_NAME_INVALID;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import cz.restty.app.entities.Project;
import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.repositories.HeaderRepository;
import cz.restty.app.repositories.ProjectRepository;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.dto.ProjectDto;
import cz.restty.app.rest.exceptions.SwaggerFileUnavailableException;
import cz.restty.app.rest.exceptions.ValidationException;
import cz.restty.app.service.EndpointService;
import cz.restty.app.service.ProjectService;
import cz.restty.app.swagger.dto.SwaggerJson;

/**
 * Default implementation of {@link ProjectService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private HeaderRepository headerRepository;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) throws RestClientException, IOException {
        SwaggerJson json = parseSource(projectDto.getSource());
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setSource(projectDto.getSource());
        project.setPath(json.getBasePath());
        projectRepository.save(project);

        json.getPaths().forEach(path -> project.addEndpoint(endpointService.createEndpoint(project, path)));

        return new ProjectDto(projectRepository.save(project));
    }

    @Override
    public Project renameProject(Long projectId, String name) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ValidationException(String.format("Project [ID=%d] does not exist.", projectId), PROJECT_NAME_INVALID));
        project.setName(name);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Project project) {
        headerRepository.deleteAllByProject(project);
        endpointRepository.deleteAllByProject(project);
        testCaseRepository.deleteAllByProject(project);
        projectRepository.delete(project);
    }

    private SwaggerJson parseSource(String source) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(source, String.class);
        if (response == null || !HttpStatus.OK.equals(response.getStatusCode())) {
            throw new SwaggerFileUnavailableException("Swagger file can not be obtained.");
        }

        return new SwaggerJson(source, response.getBody());
    }

}
