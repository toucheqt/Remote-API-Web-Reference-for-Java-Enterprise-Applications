package cz.restty.app.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import cz.restty.app.entities.Project;
import cz.restty.app.repositories.HeaderRepository;
import cz.restty.app.repositories.ProjectRepository;
import cz.restty.app.rest.dto.ProjectDto;
import cz.restty.app.rest.exceptions.SwaggerFileUnavailableException;
import cz.restty.app.service.EndpointService;
import cz.restty.app.service.ModelService;
import cz.restty.app.service.ProjectService;
import cz.restty.app.service.TestCaseService;
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
    private ModelService modelService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private HeaderRepository headerRepository;
    // TODO fixni mazani projektu

    @Override
    public Project createProject(ProjectDto projectDto) throws RestClientException, IOException {
        SwaggerJson json = parseSource(projectDto.getSource());
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setSource(projectDto.getSource());
        project.setPath(json.getBasePath());
        projectRepository.save(project);

        json.getModels().forEach(model -> project.addModel(modelService.createModel(project, model)));
        json.getPaths().forEach(path -> project.addEndpoint(endpointService.createEndpoint(project, path)));

        return projectRepository.save(project);
    }

    @Override
    public Project renameProject(Project project, String name) {
        project.setName(name);
        return projectRepository.save(project);
    }

    // fetches and parses Swagger's API file
    private SwaggerJson parseSource(String source) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(source, String.class);
        if (response == null || !HttpStatus.OK.equals(response.getStatusCode())) {
            throw new SwaggerFileUnavailableException("Swagger file can not be obtained.");
        }

        return new SwaggerJson(source, response.getBody());
    }

    @Override
    public void deleteProject(Project project) {
        modelService.deleteAllByProject(project);
        // TODO
        // headerRepository.deleteAllByProject(project);
        endpointService.deleteAllByProject(project);
        testCaseService.deleteAllByProject(project);
        projectRepository.delete(project);
    }



}
